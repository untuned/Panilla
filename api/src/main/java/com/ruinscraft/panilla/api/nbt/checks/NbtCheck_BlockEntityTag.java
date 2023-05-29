package com.ruinscraft.panilla.api.nbt.checks;

import com.ruinscraft.panilla.api.nbt.INbtTagCompound;
import com.ruinscraft.panilla.api.nbt.INbtTagList;
import com.ruinscraft.panilla.api.nbt.NbtDataType;

public class NbtCheck_BlockEntityTag implements NbtCheck {

	@Override
	public String getName() {
		return "BlockEntityTag";
	}

	@Override
	public boolean check(INbtTagCompound tag, String nmsItemClassName) {
		if (tag.hasKey(getName())) {
			INbtTagCompound blockEntityTag = tag.getCompound(getName());

			// locked chests
			if (blockEntityTag.hasKey("Lock")) {
				return false;
			}

			// signs with text
			if (blockEntityTag.hasKey("Text1")
					|| blockEntityTag.hasKey("Text2")
					|| blockEntityTag.hasKey("Text3")
					|| blockEntityTag.hasKey("Text4")) {
				return false;
			}
			
			if (blockEntityTag.hasKey("Items")) {
				if (nmsItemClassName == null 
						|| !nmsItemClassName.equals("ItemShulkerBox")) {
					return false;
				}
				
				INbtTagList items = blockEntityTag.getList("Items", NbtDataType.COMPOUND);

				for (int i = 0; i < items.size(); i++) {
					INbtTagCompound item = items.get(i);

					if (item.hasKey("tag")) {
						// TODO: check recursively
					}
				}
			}
		}

		return true;
	}

}
