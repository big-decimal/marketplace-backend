package com.marketplace.domain.banner;

import com.marketplace.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerInput {
	private long id;

	private String link;

	private int position;
	
	private UploadFile file;
}
