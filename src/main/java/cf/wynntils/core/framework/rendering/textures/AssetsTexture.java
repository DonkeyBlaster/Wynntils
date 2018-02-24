package cf.wynntils.core.framework.rendering.textures;

import cf.wynntils.core.framework.enums.ActionResult;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class AssetsTexture extends Texture {
    public ResourceLocation resourceLocation;

    public AssetsTexture(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        load();
    }

    @Override
    public ActionResult load() {
        if(loaded) return ActionResult.ISSUE;

        try {
            Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
            BufferedImage img = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream());
            width = img.getWidth();
            height = img.getHeight();
            loaded = true;
        } catch(Exception e) {
            width = -1; height = -1;
            loaded = false;
        }
        return loaded ? ActionResult.SUCCESS : ActionResult.ERROR;
    }

    @Override
    public ActionResult unload() {
        if(!loaded) return ActionResult.ISSUE;
        Minecraft.getMinecraft().getTextureManager().deleteTexture(resourceLocation);
        loaded = false;
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult bind() {
        if(!loaded) return ActionResult.ERROR;
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        return ActionResult.SUCCESS;
    }
}
