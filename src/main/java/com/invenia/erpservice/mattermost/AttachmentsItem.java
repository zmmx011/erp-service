package com.invenia.erpservice.mattermost;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachmentsItem {

	private String pretext;
	private String title;
	private String text;
}
