package com.invenia.erpservice.mattermost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachmentsItem {

	private String pretext;
	private String title;
	private String text;
	private String footer;
	@JsonProperty("footer_icon")
	private String footerIcon;
}
