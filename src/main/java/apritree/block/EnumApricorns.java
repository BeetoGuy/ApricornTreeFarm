package apritree.block;

import apritree.ApriRegistry;
import com.pixelmonmod.pixelmon.config.PixelmonItemsApricorns;
import com.pixelmonmod.pixelmon.config.PixelmonItemsPokeballs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.BiomeDictionary;

public enum EnumApricorns implements IStringSerializable
{
    BLACK("black", 0, 0x2C2C2C, 30, new BiomeDictionary.Type[] {BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.FOREST}), //Heavy Ball
    WHITE("white", 1, 0xF2F2F2, 30, new BiomeDictionary.Type[] {BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.PLAINS}), //Fast Ball
    PINK("pink", 2, 0xFF77CB, 30, new BiomeDictionary.Type[] {BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.PLAINS}), //Love Ball
    GREEN("green", 3, 0x1A830F, 30, new BiomeDictionary.Type[] {BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.JUNGLE}), //Friend Ball
    BLUE("blue", 4, 0x152DDF, 30, new BiomeDictionary.Type[] {BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.MOUNTAIN}), //Lure Ball
    YELLOW("yellow", 5, 0xE4F52A, 30, new BiomeDictionary.Type[] {BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.PLAINS}), //Moon Ball
    RED("red", 6, 0xB70606, 30, new BiomeDictionary.Type[] {BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.PLAINS}), //Level Ball
    PURPLE("purple", 7, 0x401280, 151), //Master Ball
    ORANGE("orange", 8, 0xD28521, 30), //Sport Ball
    CYAN("cyan", 9, 0x0C736A, 30), //Net Ball
    GRAY("gray", 10, 0x747474, 30), //Timer Ball
    LIGHTGRAY("light_gray", 11, 0xCECECE, 30), //Premier Ball
    BROWN("brown", 12, 0x6F512B, 30), //Safari Ball
    LIGHTBLUE("light_blue", 13, 0x69C2FA, 30), //Dive Ball
    LIME("lime", 14, 0x9EF48D, 30), //Nest Ball
    MAGENTA("magenta", 15, 0xBF48E1, 30), //Heal Ball
    GILDED("gilded", 16, 0x020202, 30), //Luxury Ball
    DARK("dark", 17, 0x020202, 30), // Dusk Ball
    SPOTTED("spotted", 18, 0xB70606, 30), //Repeat Ball
    STRIPED("striped", 19, 0x152DDF, 30), //Quick Ball
    ULTRA("ultra", 20, 0x152DDF, 30); //Beast Ball


    private String name;
    private int meta;
    private static EnumApricorns[] apricorns;
    private BiomeDictionary.Type[] biomeTypes;
    private int color;
    private int growthChance;

    private EnumApricorns(String name, int meta, int color, int growthChance, BiomeDictionary.Type[] types) {
        this.name = name;
        this.meta = meta;
        this.color = color;
        this.growthChance = growthChance;
        this.biomeTypes = types;
    }

    private EnumApricorns(String name, int meta, int color, int growthChance)
    {
        this(name, meta, color, growthChance, new BiomeDictionary.Type[0]);
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

    public int getGrowthChance() {
        return growthChance;
    }

    public static IBlockState getApriBlock(EnumApricorns apricorns) {
        int meta = apricorns.getMeta();
        if (apricorns == ULTRA) {
            return ApriRegistry.apricornUltra.getDefaultState();
        } else if (meta > 15) {
            return ApriRegistry.apricornFive.getDefaultState().withProperty(StateLibrary.APRICORNS5, apricorns);
        } else if (meta > 11) {
            return ApriRegistry.apricornFour.getDefaultState().withProperty(StateLibrary.APRICORNS4, apricorns);
        } else if (meta > 7) {
            return ApriRegistry.apricornThree.getDefaultState().withProperty(StateLibrary.APRICORNS3, apricorns);
        } else if (meta > 3) {
            return ApriRegistry.apricornTwo.getDefaultState().withProperty(StateLibrary.APRICORNS2, apricorns);
        }
        return ApriRegistry.apricornOne.getDefaultState().withProperty(StateLibrary.APRICORNS1, apricorns);
    }

    public static ItemStack getApricornFromEnum(EnumApricorns apricorn) {
        switch(apricorn) {
            case BLACK: return new ItemStack(PixelmonItemsApricorns.apricornBlack);
            case WHITE: return new ItemStack(PixelmonItemsApricorns.apricornWhite);
            case GREEN: return new ItemStack(PixelmonItemsApricorns.apricornGreen);
            case PINK: return new ItemStack(PixelmonItemsApricorns.apricornPink);
            case RED: return new ItemStack(PixelmonItemsApricorns.apricornRed);
            case YELLOW: return new ItemStack(PixelmonItemsApricorns.apricornYellow);
            case BLUE: return new ItemStack(PixelmonItemsApricorns.apricornBlue);
            case PURPLE: return new ItemStack(ApriRegistry.apricorn, 1, 0);
            case ORANGE: return new ItemStack(ApriRegistry.apricorn, 1, 4);
            case CYAN: return new ItemStack(ApriRegistry.apricorn, 1, 5);
            case GRAY: return new ItemStack(ApriRegistry.apricorn, 1, 6);
            case LIGHTGRAY: return new ItemStack(ApriRegistry.apricorn, 1, 7);
            case BROWN: return new ItemStack(ApriRegistry.apricorn, 1, 8);
            case LIGHTBLUE: return new ItemStack(ApriRegistry.apricorn, 1, 9);
            case LIME: return new ItemStack(ApriRegistry.apricorn, 1, 10);
            case MAGENTA: return new ItemStack(ApriRegistry.apricorn, 1, 11);
            case GILDED: return new ItemStack(ApriRegistry.apricorn, 1, 12);
            case DARK: return new ItemStack(ApriRegistry.apricorn, 1, 13);
            case SPOTTED: return new ItemStack(ApriRegistry.apricorn, 1, 14);
            case STRIPED: return new ItemStack(ApriRegistry.apricorn, 1, 15);
            default: return new ItemStack(ApriRegistry.apricorn, 1, 2);
        }
    }

    public static ItemStack getPokeballFromEnum(EnumApricorns apricorn) {
        switch(apricorn) {
            case BLACK: return new ItemStack(PixelmonItemsPokeballs.heavyBall);
            case WHITE: return new ItemStack(PixelmonItemsPokeballs.fastBall);
            case GREEN: return new ItemStack(PixelmonItemsPokeballs.friendBall);
            case PINK: return new ItemStack(PixelmonItemsPokeballs.loveBall);
            case RED: return new ItemStack(PixelmonItemsPokeballs.levelBall);
            case YELLOW: return new ItemStack(PixelmonItemsPokeballs.moonBall);
            case BLUE: return new ItemStack(PixelmonItemsPokeballs.lureBall);
            case PURPLE: return new ItemStack(PixelmonItemsPokeballs.masterBall);
            case ORANGE: return new ItemStack(PixelmonItemsPokeballs.sportBall);
            case CYAN: return new ItemStack(PixelmonItemsPokeballs.netBall);
            case GRAY: return new ItemStack(PixelmonItemsPokeballs.timerBall);
            case LIGHTGRAY: return new ItemStack(PixelmonItemsPokeballs.premierBall);
            case BROWN: return new ItemStack(PixelmonItemsPokeballs.safariBall);
            case LIGHTBLUE: return new ItemStack(PixelmonItemsPokeballs.diveBall);
            case LIME: return new ItemStack(PixelmonItemsPokeballs.nestBall);
            case MAGENTA: return new ItemStack(PixelmonItemsPokeballs.healBall);
            case GILDED: return new ItemStack(PixelmonItemsPokeballs.luxuryBall);
            case DARK: return new ItemStack(PixelmonItemsPokeballs.duskBall);
            case SPOTTED: return new ItemStack(PixelmonItemsPokeballs.repeatBall);
            case STRIPED: return new ItemStack(PixelmonItemsPokeballs.quickBall);
            default: return new ItemStack(PixelmonItemsPokeballs.beastBall);
        }
    }

    public static ItemStack getDiscFromEnum(EnumApricorns apricorn) {
        switch(apricorn) {
            case BLACK: return new ItemStack(PixelmonItemsPokeballs.heavyBallDisc);
            case WHITE: return new ItemStack(PixelmonItemsPokeballs.fastBallDisc);
            case GREEN: return new ItemStack(PixelmonItemsPokeballs.friendBallDisc);
            case PINK: return new ItemStack(PixelmonItemsPokeballs.loveBallDisc);
            case RED: return new ItemStack(PixelmonItemsPokeballs.levelBallDisc);
            case YELLOW: return new ItemStack(PixelmonItemsPokeballs.moonBallDisc);
            case BLUE: return new ItemStack(PixelmonItemsPokeballs.lureBallDisc);
            case PURPLE: return new ItemStack(ApriRegistry.masterball, 1, 0);
            case ORANGE: return new ItemStack(PixelmonItemsPokeballs.sportBallDisc);
            case CYAN: return new ItemStack(PixelmonItemsPokeballs.netBallDisc);
            case GRAY: return new ItemStack(PixelmonItemsPokeballs.timerBallDisc);
            case LIGHTGRAY: return new ItemStack(PixelmonItemsPokeballs.premierBallDisc);
            case BROWN: return new ItemStack(PixelmonItemsPokeballs.safariBallDisc);
            case LIGHTBLUE: return new ItemStack(PixelmonItemsPokeballs.diveBallDisc);
            case LIME: return new ItemStack(PixelmonItemsPokeballs.nestBallDisc);
            case MAGENTA: return new ItemStack(PixelmonItemsPokeballs.healBallDisc);
            case GILDED: return new ItemStack(PixelmonItemsPokeballs.luxuryBallDisc);
            case DARK: return new ItemStack(PixelmonItemsPokeballs.duskBallDisc);
            case SPOTTED: return new ItemStack(PixelmonItemsPokeballs.repeatBallDisc);
            case STRIPED: return new ItemStack(PixelmonItemsPokeballs.quickBallDisc);
            default: return new ItemStack(ApriRegistry.masterball, 1, 4);
        }
    }

    public static ItemStack getLidFromEnum(EnumApricorns apricorn) {
        switch(apricorn) {
            case BLACK: return new ItemStack(PixelmonItemsPokeballs.heavyBallLid);
            case WHITE: return new ItemStack(PixelmonItemsPokeballs.fastBallLid);
            case GREEN: return new ItemStack(PixelmonItemsPokeballs.friendBallLid);
            case PINK: return new ItemStack(PixelmonItemsPokeballs.loveBallLid);
            case RED: return new ItemStack(PixelmonItemsPokeballs.levelBallLid);
            case YELLOW: return new ItemStack(PixelmonItemsPokeballs.moonBallLid);
            case BLUE: return new ItemStack(PixelmonItemsPokeballs.lureBallLid);
            case PURPLE: return new ItemStack(ApriRegistry.masterball, 1, 1);
            case ORANGE: return new ItemStack(PixelmonItemsPokeballs.sportBallLid);
            case CYAN: return new ItemStack(PixelmonItemsPokeballs.netBallLid);
            case GRAY: return new ItemStack(PixelmonItemsPokeballs.timerBallLid);
            case LIGHTGRAY: return new ItemStack(PixelmonItemsPokeballs.premierBallLid);
            case BROWN: return new ItemStack(PixelmonItemsPokeballs.safariBallLid);
            case LIGHTBLUE: return new ItemStack(PixelmonItemsPokeballs.diveBallLid);
            case LIME: return new ItemStack(PixelmonItemsPokeballs.nestBallLid);
            case MAGENTA: return new ItemStack(PixelmonItemsPokeballs.healBallLid);
            case GILDED: return new ItemStack(PixelmonItemsPokeballs.luxuryBallLid);
            case DARK: return new ItemStack(PixelmonItemsPokeballs.duskBallLid);
            case SPOTTED: return new ItemStack(PixelmonItemsPokeballs.repeatBallLid);
            case STRIPED: return new ItemStack(PixelmonItemsPokeballs.quickBallLid);
            default: return new ItemStack(ApriRegistry.masterball, 1, 5);
        }
    }

    public static EnumApricorns byMeta(int meta)
    {
        if(meta < 0 || meta > 20)
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
        apricorns = new EnumApricorns[21];
        for(EnumApricorns apricorn : EnumApricorns.values())
            apricorns[apricorn.ordinal()] = apricorn;
    }
}
