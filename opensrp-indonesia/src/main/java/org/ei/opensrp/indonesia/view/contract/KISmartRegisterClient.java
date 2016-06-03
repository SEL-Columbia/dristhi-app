package org.ei.opensrp.indonesia.view.contract;

/**
 * Created by Dimas Ciputra on 3/11/15.
 */
public interface KISmartRegisterClient extends KBSmartRegisterClient {

    public String numberOfPregnancies();

    public String parity();

    public String numberOfLivingChildren();

    public String numberOfAbortions();
}
