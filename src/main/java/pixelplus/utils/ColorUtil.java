package pixelplus.utils;

public class ColorUtil
{
    public static int blendColors(int main, int blend)
    {
        float mRatio = 0.625F;
        float ratio = 0.375F;

        int mainA = main >> 24 & 0xFF;
        int mainR = (main & 0xFF0000) >> 16;
        int mainG = (main & 0xFF00) >> 8;
        int mainB = main & 0xFF;

        int blendA = blend >> 24 & 0xFF;
        int blendR = (blend & 0xFF0000) >> 16;
        int blendG = (blend & 0xFF00) >> 8;
        int blendB = blend & 0xFF;

        int a = (int)((mainA * mRatio) + (blendA * ratio));
        int r = (int)((mainR * mRatio) + (blendR * ratio));
        int g = (int)((mainG * mRatio) + (blendG * ratio));
        int b = (int)((mainB * mRatio) + (blendB * ratio));

        return a << 24 | r << 16 | g << 8 | b;
    }
}
