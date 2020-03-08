package dev.latvian.kubejs.client;

import dev.latvian.kubejs.MinecraftClass;
import dev.latvian.kubejs.player.ClientPlayerJS;
import dev.latvian.kubejs.world.ClientWorldJS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author LatvianModder
 */
public class ClientWrapper
{
	@MinecraftClass
	public Minecraft getMinecraft()
	{
		return Minecraft.getInstance();
	}

	@Nullable
	public ClientWorldJS getWorld()
	{
		return ClientWorldJS.instance;
	}

	@Nullable
	public ClientPlayerJS getPlayer()
	{
		if (ClientWorldJS.instance == null)
		{
			return null;
		}

		return ClientWorldJS.instance.clientPlayerData.getPlayer();
	}

	@Nullable
	public Screen getCurrentGui()
	{
		return getMinecraft().currentScreen;
	}

	public void setCurrentGui(Screen gui)
	{
		getMinecraft().displayGuiScreen(gui);
	}

	public void setTitle(String title)
	{
		getMinecraft().getMainWindow().func_230148_b_(title);
	}

	public void setIcon(String icon16, String icon32)
	{
		try (InputStream stream16 = Files.newInputStream(FMLPaths.GAMEDIR.get().resolve(icon16));
			 InputStream stream32 = Files.newInputStream(FMLPaths.GAMEDIR.get().resolve(icon32)))
		{
			Minecraft.getInstance().getMainWindow().setWindowIcon(stream16, stream32);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void setIcon(String icon)
	{
		setIcon(icon, icon);
	}
}