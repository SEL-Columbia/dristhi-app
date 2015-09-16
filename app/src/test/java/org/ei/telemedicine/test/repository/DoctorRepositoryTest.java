package org.ei.telemedicine.test.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.ei.telemedicine.doctor.DoctorData;
import org.ei.telemedicine.repository.DoctorRepository;
import org.ei.telemedicine.repository.Repository;
import org.ei.telemedicine.util.Session;

import java.util.Date;


public class DoctorRepositoryTest extends AndroidTestCase {
    private DoctorRepository repository;



    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new DoctorRepository();

        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository);


    }

    public void testInsertDoctor() throws Exception
    {
        String caseId = "Case_Insert";
        String pocInformation = "pocInformation_Insert";
        String pocPendingInfo = "pocPendingInfo_Insert";

        DoctorData doctorData = new DoctorData();
        doctorData.setCaseId(caseId);
        doctorData.setPOCInformation(pocInformation);
        doctorData.setFormInformation(pocPendingInfo);

        repository.add(doctorData);

        assertTrue(repository.isExistCaseId(caseId));
    }

    public void testUpdateDoctorDetails() throws Exception
    {
        String caseId = "Case_Update";
        String pocInformation = "pocInformation_Update";
        String pocPendingInfo = "pocPendingInfo_Update";
        String pocInformationNew = "pocInformation_new";

        DoctorData doctorData = new DoctorData();
        doctorData.setCaseId(caseId);
        doctorData.setPOCInformation(pocInformation);
        doctorData.setFormInformation(pocPendingInfo);

        repository.add(doctorData);

        repository.updateDetails(caseId, pocInformationNew, pocPendingInfo);

        assertEquals(pocInformationNew,repository.getPocInfo(caseId));
    }


    public void testDeleteCaseID() throws Exception
    {
        String caseId = "Case_delete";
        String pocInformation = "pocInformation_delete";
        String pocPendingInfo = "pocPendingInfo_delete";

        DoctorData doctorData = new DoctorData();
        doctorData.setCaseId(caseId);
        doctorData.setPOCInformation(pocInformation);
        doctorData.setFormInformation(pocPendingInfo);

        repository.add(doctorData);

        assertEquals(pocInformation, repository.getPocInfo(caseId));

        repository.deleteUseCaseId(caseId);

        assertFalse(repository.isExistCaseId(caseId));


    }

    public void testDeleteAllData() throws Exception
    {
        String caseId1 = "Case_V1";
        String caseId2 = "Case_V2";
        String pocInformation1 = "pocInformation1";
        String pocPendingInfo1 = "pocPendingInfo1";
        String pocInformation2 = "pocInformation2";
        String pocPendingInfo2 = "pocPendingInfo3";

        DoctorData doctorData1 = new DoctorData();
        doctorData1.setCaseId(caseId1);
        doctorData1.setFormInformation(pocPendingInfo1);
        repository.add(doctorData1);

        DoctorData doctorData2 = new DoctorData();
        doctorData2.setCaseId(caseId2);
        doctorData2.setFormInformation(pocPendingInfo2);
        repository.add(doctorData2);


        repository.deleteAll();

        assertTrue(repository.allConsultantsData().size() == 0);
    }


}
