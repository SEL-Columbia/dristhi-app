package org.ei.drishti.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Pair;
import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.CommCareForm;
import org.ei.drishti.repository.AllSettings;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.ei.drishti.util.Log.logError;

public class CommCareClientService {
    private static final String REMOTE_SIGNALLING_ACTION = "org.commcare.dalvik.api.action.ExternalAction";
    private static final String COMMCARE_SHARING_KEY_SYMETRIC = "commcare_sharing_key_symetric";
    private static final String COMMCARE_SHARING_KEY_CALLOUT = "commcare_sharing_key_callout";
    private static final String COMMCARE_ACTION = "commcareaction";
    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";
    private static final String CC_LOGIN_ACTION = "login";
    private AllSettings allSettings;
    private NavigationService navigationService;

    public CommCareClientService(AllSettings allSettings, NavigationService navigationService) {
        this.allSettings = allSettings;
        this.navigationService = navigationService;
    }

    public void start(Context context, String formId, String caseId) {
        if (!isValidFormId(formId)) {
            return;
        }

        tryCommCareLogin(context);

        navigationService.openCommCareForm(context, CommCareForm.valueOf(formId).dataFor(caseId));
    }

    private boolean isValidFormId(String formId) {
        for (CommCareForm form : CommCareForm.values()) {
            if (form.name().equals(formId)) {
                return true;
            }
        }
        return false;
    }

    public void tryCommCareLogin(Context applicationContext) {
        Intent intent = new Intent(REMOTE_SIGNALLING_ACTION);
        intent.putExtra(AllConstants.COMMCARE_SHARING_KEY_ID, allSettings.fetchCommCareKeyID());
        Bundle action = new Bundle();
        action.putString(COMMCARE_ACTION, CC_LOGIN_ACTION);
        action.putString(USERNAME_PARAM, allSettings.fetchRegisteredANM());
        action.putString(PASSWORD_PARAM, allSettings.fetchANMPassword());
        Pair<byte[], byte[]> serializedBundle = serializeBundle(action);

        intent.putExtra(COMMCARE_SHARING_KEY_SYMETRIC, serializedBundle.first);
        intent.putExtra(COMMCARE_SHARING_KEY_CALLOUT, serializedBundle.second);

        applicationContext.sendBroadcast(intent);
    }

    private Pair<byte[], byte[]> serializeBundle(Bundle b) {
        Parcel p = Parcel.obtain();
        p.setDataPosition(0);
        p.writeBundle(b);
        return encrypt(p.marshall());
    }

    private Pair<byte[], byte[]> encrypt(byte[] input) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256, new SecureRandom());
            SecretKey aesKey = generator.generateKey();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            KeySpec ks = new X509EncodedKeySpec(allSettings.fetchCommCarePublicKey());

            RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(ks);

            Cipher keyCipher = Cipher.getInstance("RSA");
            keyCipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] encryptedAesKey = keyCipher.doFinal(aesKey.getEncoded());

            Cipher dataCipher = Cipher.getInstance("AES");
            dataCipher.init(Cipher.ENCRYPT_MODE, aesKey);

            return new Pair<byte[], byte[]>(encryptedAesKey, dataCipher.doFinal(input));
        } catch (Exception e) {
            logError("Failed while encrypting CC login API bundle! " + e);
            return null;
        }
    }

    public void establishConnection(Activity activity) {
        if (allSettings.fetchCommCareKeyID() == null) {
            navigationService.requestKeyAccessFromCommCare(activity);
        } else {
            tryCommCareLogin(activity);
            navigationService.goHome(activity);
        }
    }

    public void handleCommCareKeyExchangeResponse(Context context, String keyID, byte[] publicKey) {
        allSettings.saveCommCareKeyID(keyID);
        allSettings.saveCommCarePublicKey(publicKey);
        tryCommCareLogin(context);
    }
}
