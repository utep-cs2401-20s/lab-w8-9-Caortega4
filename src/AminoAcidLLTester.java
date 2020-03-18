import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AminoAcidLLTester {
    @Test
    public void AminoAcidLLTest1(){
        AminoAcidLL dummy = AminoAcidLL.createFromRNASequence("CCGUUGGCACUGUUGUGA");
        dummy.printList();
    }
}
