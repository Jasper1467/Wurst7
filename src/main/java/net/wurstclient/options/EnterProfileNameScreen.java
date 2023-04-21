/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.options;

import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public final class EnterProfileNameScreen extends Screen
{
	private final Screen prevScreen;
	private final Consumer<String> callback;
	
	private TextFieldWidget valueField;
	private ButtonWidget doneButton;
	
	public EnterProfileNameScreen(Screen prevScreen, Consumer<String> callback)
	{
		super(Text.literal(""));
		this.prevScreen = prevScreen;
		this.callback = callback;
	}
	
	@Override
	public void init()
	{
		int x1 = width / 2 - 100;
		int y1 = 60;
		int y2 = height / 3 * 2;
		
		TextRenderer tr = client.textRenderer;
		
		valueField = new TextFieldWidget(tr, x1, y1, 200, 20, Text.literal(""));
		valueField.setText("");
		valueField.setSelectionStart(0);
		
		addSelectableChild(valueField);
		setFocused(valueField);
		valueField.setFocused(true);
		
		doneButton = ButtonWidget.builder(Text.literal("Done"), b -> done())
			.dimensions(x1, y2, 200, 20).build();
		addDrawableChild(doneButton);
	}
	
	private void done()
	{
		String value = valueField.getText();
		if(!value.isEmpty())
			callback.accept(value);
		
		client.setScreen(prevScreen);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int int_3)
	{
		switch(keyCode)
		{
			case GLFW.GLFW_KEY_ENTER:
			done();
			break;
			
			case GLFW.GLFW_KEY_ESCAPE:
			client.setScreen(prevScreen);
			break;
		}
		
		return super.keyPressed(keyCode, scanCode, int_3);
	}
	
	@Override
	public void tick()
	{
		valueField.tick();
	}
	
	@Override
	public void render(DrawableHelper helper, int mouseX, int mouseY,
		float partialTicks)
	{
		renderBackground(helper);
		helper.drawCenteredTextWithShadow(client.textRenderer,
			"Name your new profile", width / 2, 20, 0xFFFFFF);
		
		valueField.render(helper, mouseX, mouseY, partialTicks);
		super.render(helper, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean shouldPause()
	{
		return false;
	}
	
	@Override
	public boolean shouldCloseOnEsc()
	{
		return false;
	}
}
