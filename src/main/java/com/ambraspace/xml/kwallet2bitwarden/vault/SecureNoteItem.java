package com.ambraspace.xml.kwallet2bitwarden.vault;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter @NoArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE)
public class SecureNoteItem extends Item
{

    SecureNote secureNote = new SecureNote();

}
