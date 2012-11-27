package org.ei.drishti.view.contract.mapper;

import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.view.contract.Beneficiary;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BeneficiaryMapperTest {
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllEligibleCouples allEligibleCouples;
    private BeneficiaryMapper mapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mapper = new BeneficiaryMapper(allEligibleCouples, allBeneficiaries);
    }

    @Test
    public void shouldMapChildrenToBeneficiaries() throws Exception {
        Child child1 = new Child("CASE X", "MOTHER CASE X", "TC 1", "01/01/2008", "male", mapOf("some-key", "some-value"));
        Mother child1sMother = new Mother("MOTHER CASE X", "EC CASE X", "TC 1", "01-01-2006");
        EligibleCouple child1sEC = new EligibleCouple("EC CASE X", "Wife 1", "Husband 1", "EC 1", "Village 1", "SC 1", mapOf("some-key", "some-value"));
        Child child2 = new Child("CASE Y", "MOTHER CASE Y", "TC 2", "01/02/2008", "female", mapOf("some-key", "some-value"));
        Mother child2sMother = new Mother("MOTHER CASE Y", "EC CASE Y", "TC 2", "01-01-2007");
        EligibleCouple child2sEC = new EligibleCouple("EC CASE Y", "Wife 2", "Husband 2", "EC 2", "Village 2", "SC 2", mapOf("some-key", "some-value"));
        when(allBeneficiaries.findMother("MOTHER CASE X")).thenReturn(child1sMother);
        when(allEligibleCouples.findByCaseID("EC CASE X")).thenReturn(child1sEC);
        when(allBeneficiaries.findMother("MOTHER CASE Y")).thenReturn(child2sMother);
        when(allEligibleCouples.findByCaseID("EC CASE Y")).thenReturn(child2sEC);

        List<Beneficiary> beneficiaries = mapper.mapFromChild(asList(child1, child2));

        Beneficiary expectedBeneficiary1 = new Beneficiary("CASE X", "Wife 1", "Husband 1", "TC 1", "EC 1", "Village 1", false);
        Beneficiary expectedBeneficiary2 = new Beneficiary("CASE Y", "Wife 2", "Husband 2", "TC 2", "EC 2", "Village 2", false);
        assertEquals(asList(expectedBeneficiary1, expectedBeneficiary2), beneficiaries);
    }
}
