package com.invenia.erpservice.mattermost;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class Props {

	@Singular
	private List<AttachmentsItem> attachments;
}
