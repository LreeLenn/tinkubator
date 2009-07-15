package gov.lanl.cnls.linkedprocess.xmpp.farm;

import gov.lanl.cnls.linkedprocess.xmpp.vm.XmppVirtualMachine;
import gov.lanl.cnls.linkedprocess.xmpp.vm.SubmitJob;
import gov.lanl.cnls.linkedprocess.os.JobResult;
import gov.lanl.cnls.linkedprocess.os.VMScheduler;
import gov.lanl.cnls.linkedprocess.os.errors.VMWorkerNotFoundException;
import gov.lanl.cnls.linkedprocess.LinkedProcess;
import org.jivesoftware.smack.packet.IQ;

/**
 * User: marko
 * Date: Jun 26, 2009
 * Time: 4:23:32 PM
 */
public class VMJobResultHandler implements VMScheduler.VMResultHandler {

    XmppFarm xmppFarm;

    public VMJobResultHandler(XmppFarm xmppFarm) {
        this.xmppFarm = xmppFarm;
    }

    public void handleResult(JobResult result) {
        try {
            XmppVirtualMachine vm = xmppFarm.getVirtualMachine(result.getJob().getVMJID());
            SubmitJob returnSubmitJob = result.generateReturnEvalulate();
            vm.getConnection().sendPacket(returnSubmitJob);

            XmppVirtualMachine.LOGGER.info("Sent " + VMJobResultHandler.class.getName());
            XmppVirtualMachine.LOGGER.info(returnSubmitJob.toXML());

        } catch(VMWorkerNotFoundException e) {
            SubmitJob x = result.generateReturnEvalulate();
            x.setErrorType(LinkedProcess.ErrorType.INTERNAL_ERROR);
            x.setType(IQ.Type.ERROR);
            XmppVirtualMachine.LOGGER.severe("Could not find virtual machine. Thus, can not send error message");   
        }

    }
}
