package com.invenia.erpservice.keycloak.dto.user;

import java.util.List;
import lombok.Data;

@Data
public class UserRepresentation {
	private boolean totp;
	private String lastName;
	private Access access;
	private long createdTimestamp;
	private boolean enabled;
	private int notBefore;
	private List<Credential> credentials;
	private List<Object> disableableCredentialTypes;
	private boolean emailVerified;
	private String firstName;
	private List<Object> requiredActions;
	private Attributes attributes;
	private String id;
	private String email;
	private String username;
}
