/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsListener;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.wurstclient.WurstClient;
import net.wurstclient.mixinterface.IScreen;

@Mixin(StatsScreen.class)
public abstract class StatsScreenMixin extends Screen implements StatsListener
{
	private StatsScreenMixin(WurstClient wurst, Text text_1)
	{
		super(text_1);
	}
	
	@Inject(at = {@At("TAIL")}, method = {"createButtons()V"})
	private void onCreateButtons(CallbackInfo ci)
	{
		if(WurstClient.INSTANCE.getOtfs().disableOtf.shouldHideEnableButton())
			return;
		
		ButtonWidget toggleWurstButton =
			ButtonWidget.builder(Text.literal(""), this::toggleWurst)
				.dimensions(width / 2 - 152, height - 28, 150, 20).build();
		
		updateWurstButtonText(toggleWurstButton);
		addDrawableChild(toggleWurstButton);
		
		for(Drawable d : ((IScreen)this).getButtons())
		{
			if(!(d instanceof ClickableWidget))
				continue;
			
			ClickableWidget button = (ClickableWidget)d;
			
			if(!button.getMessage().getString()
				.equals(I18n.translate("gui.done")))
				continue;
			
			button.setX(width / 2 + 2);
			button.setWidth(150);
		}
	}
	
	private void toggleWurst(ButtonWidget button)
	{
		WurstClient wurst = WurstClient.INSTANCE;
		wurst.setEnabled(!wurst.isEnabled());
		
		updateWurstButtonText(button);
	}
	
	private void updateWurstButtonText(ButtonWidget button)
	{
		WurstClient wurst = WurstClient.INSTANCE;
		String text = (wurst.isEnabled() ? "Disable" : "Enable") + " Wurst";
		button.setMessage(Text.literal(text));
	}
}
