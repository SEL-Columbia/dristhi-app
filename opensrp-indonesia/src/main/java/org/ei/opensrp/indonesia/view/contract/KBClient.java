package org.ei.opensrp.indonesia.view.contract;

import com.google.common.base.Strings;

import org.apache.commons.lang3.StringUtils;
import org.ei.opensrp.util.DateUtil;
import org.ei.opensrp.view.contract.AlertDTO;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.List;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.opensrp.util.DateUtil.formatDate;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by Dimas Ciputra on 4/22/15.
 */
public class KBClient extends BidanSmartRegisterClient implements KISmartRegisterClient {

    private String entityId;
    private String name;
    private String husbandName;
    private String dateOfBirth;
    private String noIbu;
    private String village;
    private String jenisKontrasepsi;
    private String numPregnancies;
    private String parity;
    private String gravida;
    private String abortus;
    private String liveChild;
    private boolean isHighPriority;
    private String kbMethodChangeDate;
    private String photoPath;
    private List<AlertDTO> alerts;
    private String IMS;
    private String penyakitKronis;
    private String LILA;
    private String HB;
    private String keterangan1;
    private String keterangan2;
    private String tglKunjungan;

    public KBClient(String entityId, String name, String husbandName, String village, String noIbu) {
        this.entityId = entityId;
        this.name = name;
        this.husbandName = husbandName;
        this.village = village;
        this.noIbu = noIbu;
    }

    public KBClient withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public KBClient withKBMethod(String jenisKontrasepsi) {
        this.jenisKontrasepsi = jenisKontrasepsi;
        return this;
    }

    public KBClient withParity(String parity) {
        this.parity = parity;
        return this;
    }

    public KBClient withGravida(String gravida) {
        this.gravida = gravida;
        return this;
    }

    public KBClient withAbortus(String abortus) {
        this.abortus = abortus;
        return this;
    }

    public KBClient withIMS(String IMS) {
        this.IMS = IMS;
        return this;
    }

    public KBClient withLila(String LILA) {
        this.LILA = LILA;
        return this;
    }

    public KBClient withPenyakitKronis(String penyakitKronis) {
        this.penyakitKronis = penyakitKronis;
        return this;
    }

    public KBClient withHB(String HB) {
        this.HB = HB;
        return this;
    }

    public KBClient withKeterangan1(String keterangan1) {
        this.keterangan1 = keterangan1;
        return this;
    }

    public KBClient withKeterangan2(String keterangan2) {
        this.keterangan2 = keterangan2;
        return this;
    }

    public KBClient withLiveChild(String liveChild) {
        this.liveChild = liveChild;
        return this;
    }

    public KBClient withTanggalKunjungan(String tglKunjungan) {
        this.tglKunjungan = tglKunjungan;
        return this;
    }

    public String getTglKunjungan() {
        return isBlank(tglKunjungan) ? "" : formatDate(tglKunjungan);
    }

    public String getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public String getNoIbu() {
        return Strings.isNullOrEmpty(noIbu) ? "-" : noIbu;
    }

    public String getVillage() {
        return Strings.isNullOrEmpty(village) ? "" : humanize(village);
    }

    public String getJenisKontrasepsi() {
        return Strings.isNullOrEmpty(jenisKontrasepsi) ? "-" :  humanize(jenisKontrasepsi);
    }

    public String getNumPregnancies() {
        return numPregnancies;
    }

    public String getParity() {
        return parity;
    }

    public String getGravida() {
        return gravida;
    }

    public String getAbortus() {
        return abortus;
    }

    public String getLiveChild() {
        return liveChild;
    }

    public String getKbMethodChangeDate() {
        return kbMethodChangeDate;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public List<AlertDTO> getAlerts() {
        return alerts;
    }

    public String getIMS() {
        return Strings.isNullOrEmpty(IMS) ? "-" : IMS;
    }

    public String getPenyakitKronis() {
        return Strings.isNullOrEmpty(penyakitKronis) ? "-" : penyakitKronis;
    }

    public String getLILA() {
        return Strings.isNullOrEmpty(LILA) ? "-" : LILA;
    }

    public String getHB() {
        return Strings.isNullOrEmpty(HB) ? "-" : HB;
    }

    public String getKeterangan1() {
        return keterangan1;
    }

    public String getKeterangan2() {
        return keterangan2;
    }

    @Override
    public String numberOfPregnancies() {
        return null;
    }

    @Override
    public String parity() {
        return null;
    }

    @Override
    public String numberOfLivingChildren() {
        return null;
    }

    @Override
    public String numberOfAbortions() {
        return null;
    }

    @Override
    public String entityId() {
        return entityId;
    }

    @Override
    public String name() {
        return wifeName();
    }

    @Override
    public String displayName() {
        return null;
    }

    @Override
    public String village() {
        return humanize(village);
    }

    @Override
    public String wifeName() {
        return humanize(name);
    }

    @Override
    public String husbandName() {
        return humanize(husbandName);
    }

    @Override
    public int age() {
        return StringUtils.isBlank(dateOfBirth) ? 0 : Years.yearsBetween(LocalDate.parse(dateOfBirth), LocalDate.now()).getYears();
    }

    @Override
    public int ageInDays() {
        return StringUtils.isBlank(dateOfBirth) ? 0 : Days.daysBetween(LocalDate.parse(dateOfBirth), DateUtil.today()).getDays();
    }

    @Override
    public String ageInString() {
        return "(" + age() + ")";
    }

    @Override
    public boolean isSC() {
        return false;
    }

    @Override
    public boolean isST() {
        return false;
    }

    @Override
    public boolean isHighPriority() {
        return false;
    }

    @Override
    public boolean isBPL() {
        return false;
    }

    @Override
    public String profilePhotoPath() {
        return null;
    }

    @Override
    public String locationStatus() {
        return null;
    }

    @Override
    public boolean satisfiesFilter(String filterCriterion) {
        return name.toLowerCase(Locale.getDefault()).startsWith(filterCriterion.toLowerCase())
                || husbandName.toLowerCase(Locale.getDefault()).startsWith(filterCriterion.toLowerCase())
                || jenisKontrasepsi.toLowerCase(Locale.getDefault()).startsWith(filterCriterion.toLowerCase())
                || String.valueOf(noIbu).startsWith(filterCriterion);
    }

    @Override
    public int compareName(SmartRegisterClient client) {
        return this.wifeName().compareToIgnoreCase(client.wifeName());
    }

    @Override
    public String kbMethod() {
        return Strings.isNullOrEmpty(jenisKontrasepsi) ? "-" :  humanize(jenisKontrasepsi);
    }

    @Override
    public String kbDate() {
        return kbMethodChangeDate;
    }
}
