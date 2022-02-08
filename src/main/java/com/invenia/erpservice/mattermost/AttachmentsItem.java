package com.invenia.erpservice.mattermost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachmentsItem {

  private String fallback;
  private String color;
  private String pretext;
  private String title;
  @JsonProperty("title_link")
  private String titleLink;
  private String text;
  private String footer;
  @JsonProperty("footer_icon")
  private String footerIcon;
  @JsonProperty("author_name")
  private String authorName;
  @JsonProperty("author_icon")
  private String authorIcon;
  @JsonProperty("author_link")
  private String authorLink;
  @JsonProperty("image_url")
  private String imageUrl;
}
