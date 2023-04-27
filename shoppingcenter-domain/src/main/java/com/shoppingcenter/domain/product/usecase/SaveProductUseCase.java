package com.shoppingcenter.domain.product.usecase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductImageDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class SaveProductUseCase {

    private ProductDao productDao;

    private ProductImageDao imageDao;

    private ProductVariantDao variantDao;

    private ShopDao shopDao;
    
    private CategoryDao categoryDao;

    private FileStorageAdapter fileStorageAdapter;

    private HTMLStringSanitizer htmlStringSanitizer;

    public void apply(Product product) {

        if (!Utils.hasText(product.getName())) {
            throw new ApplicationException("Required product name");
        }

        if (!Utils.hasText(product.getSlug())) {
            throw new ApplicationException("Required product slug");
        }

        if (!categoryDao.existsById(product.getCategoryId())) {
            throw new ApplicationException("Required category");
        }

        if (!shopDao.existsById(product.getShopId())) {
            throw new ApplicationException("Required shop");
        }

        if (Utils.hasText(product.getDescription())) {
            var desc = product.getDescription();
            product.setDescription(htmlStringSanitizer.sanitize(desc));
        }
        
        var slug = Utils.convertToSlug(product.getName());
        
        if (!Utils.hasText(slug)) {
        	throw new ApplicationException("Invalid slug value");
        }
        
        product.setSlug(slug);
        
        var images = Optional.ofNullable(product.getImages()).orElseGet(ArrayList::new);

        boolean atLeastOneImage = images.stream().anyMatch(img -> img.isDeleted() == false);

        if (!atLeastOneImage) {
            throw new ApplicationException("At least one image required");
        }

        var variants = Optional.ofNullable(product.getVariants()).orElseGet(ArrayList::new);

        if (product.isWithVariant() && variants.size() > 0) {
        	product.setPrice(variants.stream().mapToDouble(ProductVariant::getPrice).min().orElse(0));
        }

        var productId = productDao.save(product);

        var deletedImages = new ArrayList<String>();
        var uploadedImages = new HashMap<String, UploadFile>();

        var deletedImageList = new ArrayList<Long>();
        var uploadedImageList = new ArrayList<ProductImage>();

        var thumbnail = product.getThumbnail();

        for (var image : images) {
            if (image.isDeleted()) {
                deletedImages.add(image.getName());
                deletedImageList.add(image.getId());
                // imageDao.delete(image.getId());
                continue;
            }

            if (image.getId() <= 0 && (image.getFile() == null || image.getFile().getSize() <= 0)) {
                throw new ApplicationException("Uploaded image file must not empty");
            }

            if (image.getFile() != null && image.getFile().getSize() > 0) {
                image.setSize(image.getFile().getSize());
                var timestamp = System.currentTimeMillis();
                String suffix = image.getFile().getExtension();
                String imageName = String.format("%d_%d_%s.%s", productId, timestamp,
                        Utils.generateRandomCode(8), suffix);

                image.setName(imageName);

                uploadedImages.put(imageName, image.getFile());
            }

            if (image.isThumbnail()) {
                thumbnail = image.getName();
            }

            image.setProductId(productId);

            // imageDao.save(image);
            uploadedImageList.add(image);
        }

        imageDao.deleteAll(deletedImageList);
        imageDao.saveAll(uploadedImageList);

        if (thumbnail == null) {
            var imageName = uploadedImages.keySet().stream().findFirst().orElseGet(() -> {
                return product.getImages().get(0).getName();
            });
            productDao.updateThumbnail(productId, imageName);
        } else if (product.getThumbnail() == null || !thumbnail.equals(product.getThumbnail())) {
            productDao.updateThumbnail(productId, thumbnail);
        }

        var deletedVariantList = new ArrayList<Long>();
        var variantList = new ArrayList<ProductVariant>();

        for (var variant : variants) {
            if (variant.isDeleted()) {
                // variantDao.delete(variant.getId());
                deletedVariantList.add(variant.getId());
                continue;
            }

            if (variant.getOptions() == null || variant.getOptions().isEmpty()) {
                throw new ApplicationException("Invalid variant options");
            }

//            var pv = new ProductVariant();
//            pv.setId(variant.getId());
//            pv.setTitle(variant.getTitle());
//            pv.setOptions(variant.getOptions());
//            pv.setPrice(variant.getPrice());
//            pv.setSku(variant.getSku());
//            pv.setStockLeft(variant.getStockLeft());
            
            variant.setProductId(productId);

            // variantDao.save(variant);
            variantList.add(variant);
        }

        variantDao.deleteAll(deletedVariantList);
        variantDao.saveAll(variantList);

        var dir = Constants.IMG_SHOP_ROOT + File.separator + product.getShopId() + File.separator + Constants.IMG_PRODUCT_ROOT;

        fileStorageAdapter.write(uploadedImages.entrySet(), dir);

        if (deletedImages.size() > 0) {
            fileStorageAdapter.delete(dir, deletedImages);
        }
    }

}