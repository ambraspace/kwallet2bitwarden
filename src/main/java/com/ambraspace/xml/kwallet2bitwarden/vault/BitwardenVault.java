package com.ambraspace.xml.kwallet2bitwarden.vault;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter @NoArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE)
public class BitwardenVault
{

    List<Item> items = new ArrayList<Item>();


}
