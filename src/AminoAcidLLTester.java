import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AminoAcidLLTester {
    @Test
    public void AminoAcidLLTest1(){
        //Crete from RNA sequence test for regular performance
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

    @Test
    public void AminoAcidLLTest6(){
        //Test for aminoAcidCounts regular performance test
        //RNA sequence GGGEEA: 3 G's, 2 E's, 1 A
        //expected output array = {3, 2, 1}
        int[] expected = {3, 2, 1};
        String testSequence = "GGAGGAGGAGAAGAAGCUUAG";
        AminoAcidLL dummy = AminoAcidLL.createFromRNASequence(testSequence);
        assertArrayEquals(expected, dummy.aminoAcidCounts());
    }

    @Test
    public void AminoAcidLLTest7(){
        //Test for aminoAcidCompare, regular performance test
        //Test list1 = AAATTCDD
        //Test list2 = GGGAAATTD
        //Expected difference: 5
        AminoAcidLL list1 = AminoAcidLL.createFromRNASequence("GAGGAGGAGACCACCUGCGACGACUAG");
        AminoAcidLL list2 = AminoAcidLL.createFromRNASequence("GGUGGUGGUGAGGAGGAGACCACCGACUAG");
        list1 = AminoAcidLL.sort(list1);
        list2 = AminoAcidLL.sort(list2);
        assertEquals(5, list1.aminoAcidCompare(list2));
    }

    @Test
    public void AminoAcidLLTest8(){
        //Test for aminoAcidCompare, different amino acids
        //Test list1 = GEAV
        //Test list2 = GGGAAATTD
        //Expected difference: 9
        AminoAcidLL list1 = AminoAcidLL.createFromRNASequence("GGGGAGGCGGUGUGA");
        AminoAcidLL list2 = AminoAcidLL.createFromRNASequence("GGUGGUGGUGAGGAGGAGACCACCGACUAG");
        list1 = AminoAcidLL.sort(list1);
        list2 = AminoAcidLL.sort(list2);
        assertEquals(9, list1.aminoAcidCompare(list2));
    }

    @Test
    public void AminoAcidLLTest9(){
        //Test for aminoAcidCompare with an empty list, edge case test
        //Test list2 = nothing
        //Test list1 = GGGAAATTD
        //Expected difference: 9
        AminoAcidLL list2 = AminoAcidLL.createFromRNASequence("UAG");
        AminoAcidLL list1 = AminoAcidLL.createFromRNASequence("GGUGGUGGUGAGGAGGAGACCACCGACUAG");
        list1 = AminoAcidLL.sort(list1);
        list2 = AminoAcidLL.sort(list2);
        assertEquals(9, list1.aminoAcidCompare(list2));
    }

    @Test
    public void AminoAcidLLTest10(){
        //Test for codonCompare, where they have the same aminoacids
        //Test list1 =  "GEAA"
        //Test list2 =  "GGEAA"
        //Did not repeat any codons
        AminoAcidLL list1 = AminoAcidLL.createFromRNASequence("GGGGAGGCCGCUUAG");
        AminoAcidLL list2 = AminoAcidLL.createFromRNASequence("GGAGGCGAAGCGGCAUAG");
        list1 = AminoAcidLL.sort(list1);
        list2 = AminoAcidLL.sort(list2);
        assertEquals(9, list1.codonCompare(list2));
    }

    @Test
    public void AminoAcidLLTest11(){
        //Test for codonCompare, where they have aminoacids with different codon combinations
        //Test list1 =  "GEAAQHP"
        //Test list2 =  "GGEAAQRF"
        //Did not repeat any codons
        AminoAcidLL list1 = AminoAcidLL.createFromRNASequence("GGGCAGCACCCAGAGGCCGCUUAG");
        AminoAcidLL list2 = AminoAcidLL.createFromRNASequence("GGACAGCGAUUCGGCGAAGCGGCAUAG");
        list1 = AminoAcidLL.sort(list1);
        list2 = AminoAcidLL.sort(list2);
        assertEquals(13, list1.codonCompare(list2));
    }
}
