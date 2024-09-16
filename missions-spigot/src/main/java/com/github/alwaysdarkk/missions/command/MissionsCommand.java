package com.github.alwaysdarkk.missions.command;

import com.github.alwaysdarkk.missions.common.command.CustomCommand;
import com.github.alwaysdarkk.missions.view.MissionsView;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MissionsCommand extends CustomCommand {

    private final ViewFrame viewFrame;

    public MissionsCommand(ViewFrame viewFrame) {
        super("missoes", null, true);
        this.viewFrame = viewFrame;
    }

    @Override
    protected void onCommand(CommandSender commandSender, String[] arguments) {
        final Player player = (Player) commandSender;
        viewFrame.open(MissionsView.class, player);
    }
}
