import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AminoAcidLLTester {
    @Test
    public void AminoAcidLLTest1(){
        //Expected codon sequence: "GAEFCHDILMNPQRVWST"
        String expected = "GAEFCHDILMNPQRVWST";
        AminoAcidLL dummy = AminoAcidLL.createFromRNASequence("GGGGCCGAGUUCUGCCACGACAUACUCAUGAACCCCCAGCGUGUGUGGAGCACGUAG");
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), dummy.aminoAcid);
            dummy = dummy.next;
        }

    }

    @Test
    public void AminoAcidLLTest2(){
        //Test for isSorted when the list is not sorted
        //expected: false
        //Codon sequence : "GAEFCHDILMNPQRVWST"
        AminoAcidLL dummy = AminoAcidLL.createFromRNASequence("GGGGCCGAGUUCUGCCACGACAUACUCAUGAACCCCCAGCGUGUGUGGAGCACGUAG");
        assertEquals(false, dummy.isSorted());

    }

    @Test
    public void AminoAcidLLTest3(){
        //Test for isSorted when the list is sorted
        //expected: true
        //Codon sequence : "STV"
        AminoAcidLL dummy = AminoAcidLL.createFromRNASequence("ACGUAGUGG");
        assertEquals(true, dummy.isSorted());

    }

    @Test
    public void AminoAcidLLTest4(){
        //Test for sort with help of isSorted()
        //expected: true
        //Codon sequence before sort: "GAEFCHDILMNPQRVWST"
        AminoAcidLL dummy = AminoAcidLL.createFromRNASequence("GGGGCCGAGUUCUGCCACGACAUACUCAUGAACCCCCAGCGUGUGUGGAGCACGUAG");
        dummy = AminoAcidLL.sort(dummy);
        assertEquals(true, dummy.isSorted());

    }

    @Test
    public void AminoAcidLLTest5(){
        //Test for aminoAcidList regular performance test
        char[] expected = {'G','A','E','F','C','H','D','I','L','M','N','P','Q','R','V','W','S','T'};
        AminoAcidLL dummy = AminoAcidLL.createFromRNASequence("GGGGCCGAGUUCUGCCACGACAUACUCAUGAACCCCCAGCGUGUGUGGAGCACGUAG");
        assertArrayEquals(expected, dummy.aminoAcidList());
    }
}
