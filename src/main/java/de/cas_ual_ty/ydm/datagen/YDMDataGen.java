package de.cas_ual_ty.ydm.datagen;

import de.cas_ual_ty.ydm.YDM;
import de.cas_ual_ty.ydm.card.CardSleevesType;
import de.cas_ual_ty.ydm.clientutil.ImageHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider.Factory;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.io.IOException;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class YDMDataGen
{
    @SubscribeEvent
    public static <T> void gatherData(GatherDataEvent event)
    {
        try
        {
            ImageHandler.createCustomSleevesImages(CardSleevesType.DUELING_MC, "png");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        DataGenerator generator = event.getGenerator();
        ((DataGenerator) generator).addProvider(event.includeClient(), (Factory<?>) new YDMItemModels(generator, YDM.MOD_ID, event.getExistingFileHelper()));
    }
}