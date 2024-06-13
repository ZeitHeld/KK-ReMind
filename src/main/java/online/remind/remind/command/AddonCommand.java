package online.remind.remind.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Collection;

public class AddonCommand {

    public static Collection<ServerPlayer> getPlayers(CommandContext<CommandSourceStack> context, int numOfParams) throws CommandSyntaxException {
        Collection<ServerPlayer> players = new ArrayList<>();
        if(context.getInput().split(" ").length == numOfParams) {
            players.add(context.getSource().getPlayerOrException());
        } else {
            players = EntityArgument.getPlayers(context, "targets");
        }
        return players;
    }
}
