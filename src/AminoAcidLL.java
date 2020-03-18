class AminoAcidLL{
  char aminoAcid;
  String[] codons;
  int[] counts;
  AminoAcidLL next;

  AminoAcidLL(){

  }


  /********************************************************************************************/
  /* Creates a new node, with a given amino acid/codon 
   * pair and increments the codon counter for that codon.
   * NOTE: Does not check for repeats!! */
  AminoAcidLL(String inCodon){
    this.aminoAcid = AminoAcidResources.getAminoAcidFromCodon(inCodon);
    this.codons = AminoAcidResources.getCodonListForAminoAcid(aminoAcid);
    this.counts = new int[codons.length];
    for (int i = 0; i < codons.length; i++) {
      if (codons[i].equals(inCodon))
        counts[i]++;
    }

    this.next = null;
  }

  /********************************************************************************************/
  /* Recursive method that increments the count for a specific codon:
   * If it should be at this node, increments it and stops, 
   * if not passes the task to the next node. 
   * If there is no next node, add a new node to the list that would contain the codon. 
   */
  /*******************************************/
  /*******************************************/
  /*******************************************/
  /********change public to private used for testing***********************************/
  public void addCodon(String inCodon){
    if(this.aminoAcid == AminoAcidResources.getAminoAcidFromCodon(inCodon)) {
      for (int i = 0; i < this.codons.length; i++) {
        if (inCodon.equals(this.codons[i])) {
          counts[i]++;
          return;
        }
      }
    }

    if(this.next == null){
      this.next = new AminoAcidLL(inCodon);
      return;
    }

    this.next.addCodon(inCodon);

  }


  /********************************************************************************************/
  /* Shortcut to find the total number of instances of this amino acid */
  private int totalCount(){
    int sum = 0;
    for (int i = 0; i < this.counts.length; i++) {
      sum += counts[i];
    }
    return sum;
  }


  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
  *  must be matching, but this is not tracked */
  private int totalDiff(AminoAcidLL inList){
    return Math.abs(totalCount() - inList.totalCount());
  }


  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
  *  must be matching, but this is not tracked */
  private int codonDiff(AminoAcidLL inList){
    int diff = 0;
    for(int i=0; i<codons.length; i++){
      diff += Math.abs(counts[i] - inList.counts[i]);
    }
    return diff;
  }

  /********************************************************************************************/
  /* Recursive method that finds the differences in **Amino Acid** counts. 
   * the list *must* be sorted to use this method */
  public int aminoAcidCompare(AminoAcidLL inList) {
    if(this.aminoAcid == inList.aminoAcid)
      return Math.abs(inList.totalCount() - this.totalCount());
    return aminoAcidCompare(inList.next);
  }

  /********************************************************************************************/
  /* Same as above, but counts the codon usage differences
   * Must be sorted. */
  public int codonCompare(AminoAcidLL inList){

    return 0;
  }


  /********************************************************************************************/
  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList(){
    return new char[]{};
  }

  /********************************************************************************************/
  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts(){
    return new int[]{};
  }


  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted(){
    return false;
  }


  /********************************************************************************************/
  /* Static method for generating a linked list from an RNA sequence */
  public static AminoAcidLL createFromRNASequence(String inSequence){
    //If the sequence has less than the length of a codon it returns null
    if (inSequence.length() < 3){
      return null;
    }
    //Gets the first codon
    String nextCodon = inSequence.substring(0, 3);
    //Create the head reference memory allocation
    AminoAcidLL head = null;
    //Keep adding codons until it finds a STOP or an invalid codon
    while ((AminoAcidResources.getAminoAcidFromCodon(nextCodon) != '*') && (AminoAcidResources.getAminoAcidFromCodon(nextCodon) != (char)0)){
      //If the list is empty create the head and change the what the next codon is
      if (head == null){
        head = new AminoAcidLL(nextCodon);
        inSequence = inSequence.substring(3);
        if(inSequence.length() < 2) {
          nextCodon = inSequence;
        }else {
          nextCodon = inSequence.substring(0, 3);
        }

      }else {
        //Add a codon to the list and change what the next codon is
        head.addCodon(nextCodon);

        inSequence = inSequence.substring(3);
        nextCodon = (inSequence.length() == 3) ? inSequence : inSequence.substring(0,3);
      }
    }

      return head;
  }


  /********************************************************************************************/
  /* sorts a list by amino acid character*/
  public static AminoAcidLL sort(AminoAcidLL inList){
    return null;
  }

  /********************************************************************************************/
  public void printList(){
    AminoAcidLL pointer = this;
    while(pointer != null) {
      System.out.print(pointer.aminoAcid);
      pointer = pointer.next;
    }
  }
}