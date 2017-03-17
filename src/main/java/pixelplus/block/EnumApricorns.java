package pixelplus.block;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.BiomeDictionary;

public enum EnumApricorns implements IStringSerializable
{
    BLACK("black", 0, 0x020202, new BiomeDictionary.Type[] {BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.FOREST}),
    WHITE("white", 1, 0xF2F2F2, new BiomeDictionary.Type[] {BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.PLAINS}),
    PINK("pink", 2, 0xFF77CB, new BiomeDictionary.Type[] {BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.PLAINS}),
    GREEN("green", 3, 0x1A830F, new BiomeDictionary.Type[] {BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.JUNGLE}),
    BLUE("blue", 4, 0x152DDF, new BiomeDictionary.Type[] {BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.MOUNTAIN}),
    YELLOW("yellow", 5, 0xE4F52A, new BiomeDictionary.Type[] {BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.PLAINS}),
    RED("red", 6, 0xB70606, new BiomeDictionary.Type[] {BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.PLAINS}),
    PURPLE("purple", 7, 0x401280);

    private String name;
    private int meta;
    private static EnumApricorns[] apricorns;
    private BiomeDictionary.Type[] biomeTypes;
    private int color;

    private EnumApricorns(String name, int meta, int color, BiomeDictionary.Type[] types) {
        this.name = name;
        this.meta = meta;
        this.color = color;
        this.biomeTypes = types;
    }

    private EnumApricorns(String name, int meta, int color)
    {
        this(name, meta, color, new BiomeDictionary.Type[0]);
    }

    @Override
    public String getName()
    {
        return name;
    }

    public int getMeta()
    {
        return meta;
    }

    public int getColor()
    {
        return color;
    }

    public static EnumApricorns byMeta(int meta)
    {
        if(meta < 0 || meta > 7)
            return apricorns[0];
        return apricorns[meta];
    }

    public BiomeDictionary.Type[] getBiomeTypes() {
        if (biomeTypes.length > 0)
            return biomeTypes;
        return null;
    }

    static
    {
        apricorns = new EnumApricorns[8];
        for(EnumApricorns apricorn : EnumApricorns.values())
            apricorns[apricorn.getMeta()] = apricorn;
    }
}
