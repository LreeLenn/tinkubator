package gov.lanl.cnls.linkedprocess.xmpp.lopfarm;

import gov.lanl.cnls.linkedprocess.os.errors.VMWorkerNotFoundException;
import gov.lanl.cnls.linkedprocess.LinkedProcess;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.IQ;

/**
 * User: marko
 * Date: Jun 25, 2009
 * Time: 2:25:59 PM
 */
public class TerminateVmListener implements PacketListener {

    private XmppFarm farm;

    public TerminateVmListener(XmppFarm farm) {
        this.farm = farm;
    }

    public void processPacket(Packet terminateVm) {

        try {
            processPacketTemp(terminateVm);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processPacketTemp(Packet terminateVm) {
        XmppFarm.LOGGER.info("Arrived DestroyListener:");
        XmppFarm.LOGGER.info(terminateVm.toXML());

        TerminateVm returnTerminateVm = new TerminateVm();
        returnTerminateVm.setTo(terminateVm.getFrom());
        returnTerminateVm.setPacketID(terminateVm.getPacketID());

        String vmJid = ((TerminateVm) terminateVm).getVmJid();
        try {
            farm.destroyVirtualMachine(vmJid);
        } catch (VMWorkerNotFoundException e) {
            returnTerminateVm.setVmJid(vmJid);
            returnTerminateVm.setErrorType(LinkedProcess.Errortype.INTERNAL_ERROR);
            returnTerminateVm.setType(IQ.Type.RESULT);
        }

        XmppFarm.LOGGER.info("Sent DestroyListener:");
        XmppFarm.LOGGER.info(returnTerminateVm.toXML());
        farm.getConnection().sendPacket(returnTerminateVm);
    }
}