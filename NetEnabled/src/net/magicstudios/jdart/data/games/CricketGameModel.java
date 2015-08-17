package net.magicstudios.jdart.data.games;

import java.util.*;
import net.magicstudios.jdart.data.service.*;

import net.magicstudios.jdart.data.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public class CricketGameModel extends GameModel {

    private boolean m_bGivePoints = true;

    public CricketGameModel(boolean give) {
      m_bGivePoints = give;
    }

    public void initialize() {

    }

    public void score(Zone zone) {
        if (zone.getRingMultiplier() != Zone.RING_OUTSIDE && (zone.getSlice() >= 15 || zone.getSlice() == 0) && ! isWinner()) {

            DAPlayer playerCurrent = getCurrentPlayer();
            int points = 0;
            if (playerCurrent.isZoneClosed(zone)) {
                points = zone.getPoints();
            } else {
                points = playerCurrent.addZone(zone);
            }

            if (playerCurrent.isZoneClosed(zone) && points > 0) {
                if (isGivePoints()) {
                    int count = this.getPlayerCount();
                    for (int i = 0; i < count; i++) {
                        DAPlayer playerOther = getPlayer(i);
                        if (!playerOther.isZoneClosed(zone)) {
                            playerOther.addScore(points);
                        }
                    }
                } else {
                    playerCurrent.addScore(points);
                }
            }
        }
    }

    public boolean isGivePoints() {
        return m_bGivePoints;
    }

    public void setGivePoints(boolean state) {
        m_bGivePoints = state;
    }

    public boolean isWinner() {
        return getWinner() != null;
    }

    public boolean isTie() {
        return getTies().size() > 1;
    }

    public Vector getTies() {
        Vector vecTies = new Vector();
        int count = this.getPlayerCount();
        for (int i = 0; i < count; i++) {
            DAPlayer playerOther = getPlayer(i);
            if (playerOther.isAllZonesClosed()) {
                if ((isGivePoints() && isLowestScore(playerOther, true)) ||
                    (! isGivePoints() && isHighestScore(playerOther, true))) {
                    vecTies.add(playerOther);
                }
            }
        }

        return vecTies;
    }

    public DAPlayer getWinner() {
        DAPlayer playerWinner = null;
        int count = this.getPlayerCount();
        for (int i = 0; i < count; i++) {
            DAPlayer playerOther = getPlayer(i);
            if (playerOther.isAllZonesClosed()) {
                if ((isGivePoints() && isLowestScore(playerOther)) ||
                    (! isGivePoints() && isHighestScore(playerOther))) {
                    playerWinner = playerOther;
                }
            }
        }

        return playerWinner;
    }

    public boolean isLowestScore(DAPlayer p) {
        return isLowestScore(p, false);
    }

    public boolean isLowestScore(DAPlayer p, boolean tieOK) {
        boolean bIsLowest = true;
        int count = this.getPlayerCount();
        for (int i = 0; i < count; i++) {
            DAPlayer playerOther = getPlayer(i);
            if (p != playerOther &&
                ((playerOther.getScore() <= p.getScore() && !tieOK) ||
                 (playerOther.getScore() < p.getScore() && tieOK))) {
                bIsLowest = false;
                break;
            }
        }
        return bIsLowest;
    }

    public boolean isHighestScore(DAPlayer p) {
        return isHighestScore(p, false);
    }

    public boolean isHighestScore(DAPlayer p, boolean tieOK) {
        boolean bIsHighest = true;
        int count = this.getPlayerCount();
        for (int i = 0; i < count; i++) {
            DAPlayer playerOther = getPlayer(i);
            if (p != playerOther &&
                ((playerOther.getScore() >= p.getScore() && !tieOK) ||
                 (playerOther.getScore() > p.getScore() && tieOK))) {

                bIsHighest = false;
                break;
            }
        }
        return bIsHighest;
    }
}

