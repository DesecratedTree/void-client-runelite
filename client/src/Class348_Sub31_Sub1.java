/* Class348_Sub31_Sub1 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

import java.awt.*;
import java.awt.image.*;
import java.util.Hashtable;

final class Class348_Sub31_Sub1 extends Class348_Sub31 {
    private Shape aShape9064;
    private Canvas aCanvas9065;
    private Image anImage9066;
    private Rectangle aRectangle9067;

    final void method3011(int i, int i_0_, int i_1_, Graphics graphics, int i_2_, int i_3_, int i_4_, int i_5_) {
        if (Applet_Sub1.shouldScaleCanvasFrame()) {
            drawScaled(i, i_0_, i_1_, graphics, i_2_, i_3_, i_4_, i_5_);
            return;
        }

        aShape9064 = graphics.getClip();
        aRectangle9067.x = i_3_;
        aRectangle9067.width = i_4_;
        aRectangle9067.y = i;
        aRectangle9067.height = i_1_;
        graphics.setClip(aRectangle9067);
        graphics.drawImage(anImage9066, i_3_ + -i_0_, -i_5_ + i, aCanvas9065);
        if (i_2_ == -1) graphics.setClip(aShape9064);
    }

    private void drawScaled(int y, int sourceX, int height, Graphics graphics, int restoreClip, int x, int width, int sourceY) {
        int canvasWidth = Math.max(1, aCanvas9065.getWidth());
        int canvasHeight = Math.max(1, aCanvas9065.getHeight());
        double scaleX = (double) canvasWidth / Math.max(1, this.anInt6917);
        double scaleY = (double) canvasHeight / Math.max(1, this.anInt6920);

        int destX1 = (int) Math.floor(x * scaleX);
        int destY1 = (int) Math.floor(y * scaleY);
        int destX2 = (int) Math.ceil((x + width) * scaleX);
        int destY2 = (int) Math.ceil((y + height) * scaleY);

        aShape9064 = graphics.getClip();
        graphics.setClip(destX1, destY1, Math.max(1, destX2 - destX1), Math.max(1, destY2 - destY1));

        Object previousHint = null;
        Graphics2D graphics2D = graphics instanceof Graphics2D ? (Graphics2D) graphics : null;
        if (graphics2D != null) {
            previousHint = graphics2D.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, Applet_Sub1.useFastCanvasScaling()
                    ? RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
                    : RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        }

        graphics.drawImage(anImage9066, destX1, destY1, destX2, destY2, sourceX, sourceY, sourceX + width, sourceY + height, aCanvas9065);

        if (graphics2D != null) {
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, previousHint);
        }
        if (restoreClip == -1) graphics.setClip(aShape9064);
    }

    final void method3008(Canvas canvas, int i, int i_6_, int i_7_) {
        aCanvas9065 = canvas;
        aRectangle9067 = new Rectangle();
        this.anInt6917 = i;
        this.anInt6920 = i_7_;
        this.anIntArray6916 = new int[(this.anInt6920 * this.anInt6917)];
        DataBufferInt databufferint = new DataBufferInt(this.anIntArray6916, (this.anIntArray6916).length);
        if (i_6_ > -42) method3008(null, 6, -14, 63);
        DirectColorModel directcolormodel = new DirectColorModel(32, 16711680, 65280, 255);
        WritableRaster writableraster = Raster.createWritableRaster((directcolormodel.createCompatibleSampleModel((this.anInt6917), (this.anInt6920))), databufferint, null);
        anImage9066 = new BufferedImage(directcolormodel, writableraster, false, new Hashtable());
    }
}
