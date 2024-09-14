package com.github.alwaysdarkk.missions.common.repository.parser;

import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.mongodb.parser.MongoDocumentParser;
import org.bson.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MissionUserParser extends MongoDocumentParser<MissionUser> {

    @Override
    public BsonDocument write(MissionUser missionUser) {
        final BsonDocument document = new BsonDocument();
        document.append("playerName", new BsonString(missionUser.getPlayerName()));
        document.append("currentMission", new BsonInt32(missionUser.getCurrentMission()));
        document.append("progress", new BsonDouble(missionUser.getProgress()));

        final BsonArray missionsArray = new BsonArray();
        missionUser.getCompleteMissions().forEach(missionId -> missionsArray.add(new BsonInt32(missionId)));

        document.append("completedMissions", missionsArray);

        return document;
    }

    @Override
    public @Nullable MissionUser read(BsonDocument document) {
        final String playerName = document.getString("playerName").getValue();

        final int currentMission = document.getInt32("currentMission").getValue();
        final double progress = document.getInt64("progress").getValue();

        final List<Integer> completedMissions = new ArrayList<>();

        final BsonArray missionsArray = document.getArray("completedMissions").asArray();
        missionsArray.forEach(
                missionId -> completedMissions.add(missionId.asInt32().getValue()));

        return MissionUser.builder()
                .playerName(playerName)
                .currentMission(currentMission)
                .progress(progress)
                .completeMissions(completedMissions)
                .dirty(false)
                .build();
    }
}