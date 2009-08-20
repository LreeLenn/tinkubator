package org.linkedprocess.farm;

import org.linkedprocess.LinkedProcess;
import org.linkedprocess.LopListener;

/**
 * User: marko
 * Date: Jul 24, 2009
 * Time: 10:50:06 AM
 */
public abstract class LopFarmListener extends LopListener {

    public static final LinkedProcess.ClientType LOP_CLIENT_TYPE = LinkedProcess.ClientType.FARM;

    public LopFarmListener(LopFarm lopFarm) {
        super(lopFarm);
    }

    public LopFarm getXmppFarm() {
        return (LopFarm) this.lopClient;
    }

}
