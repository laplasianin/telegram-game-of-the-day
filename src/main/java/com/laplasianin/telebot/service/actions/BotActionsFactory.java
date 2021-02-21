package com.laplasianin.telebot.service.actions;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class BotActionsFactory {

    @Inject ExpressionAction expressionAction;
    @Inject GifReactionAction gifReactionAction;
    @Inject PolitotaAction politotaAction;
    @Inject GoosiAction goosiAction;
    @Inject HyislovoAction hyislovoAction;
    @Inject LohAction lohAction;
    @Inject PidorDnyaAction pidorDnyaAction;
    @Inject BeerAction beerAction;

    public List<BotAction> provideAvailableBotActions() {
        return List.of(
                expressionAction,
                politotaAction,
                goosiAction,
                hyislovoAction,
                lohAction,
                pidorDnyaAction,
                beerAction
        );
    }
}
