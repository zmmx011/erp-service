package com.invenia.erpservice.mattermost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

	private String message;
	@JsonProperty("channel_id")
	private String channelId;
	private Props props;
}
