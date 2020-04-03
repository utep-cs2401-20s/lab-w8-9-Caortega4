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

  private void addCodon(String inCodon){
    if(this.aminoAcid == AminoAcidResources.getAminoAcidFromCodon(inCodon)) {
      //Checks for a matching combination to add 1 to that combination's count
      for (int i = 0; i < this.codons.length; i++) {
        if (inCodon.equals(this.codons[i])) {
          counts[i]++;
          return;
        }
      }
    }
    //If it reaches the end of the list it will create a new node with that aminoacid respective to that codon
    if(this.next == null){
      this.next = new AminoAcidLL(inCodon);
      return;
    }
    //Recursive call to check the next node in position
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
    //Case when there is at least a null reference
    if (this.next == null || inList == null){
      if (inList == null){
        return this.totalCount();
      }else{
        return inList.totalCount() + this.codonCompare(inList.next);
      }
    }
    //Case when aminoacids match
    if(this.aminoAcid == inList.aminoAcid){
      return this.totalDiff(inList) + this.next.aminoAcidCompare(inList.next);
    }
    //Cases when this aminoAcid has an aminoacid that inList pointer does not
    if(this.aminoAcid < inList.aminoAcid){
      return this.totalCount() + this.next.aminoAcidCompare(inList);
    }
    //Cases when inList aminoAcid has an aminoacid that this does not
    return inList.totalCount() + this.aminoAcidCompare(inList.next);
  }

  /********************************************************************************************/
  /* Same as above, but counts the codon usage differences
   * Must be sorted. */
  public int codonCompare(AminoAcidLL inList){
    //Case when there is at least a null reference
    if (this.next == null || inList == null){
      if (inList == null){
        return this.totalCount();
      }else{
        return inList.totalCount() + this.codonCompare(inList.next);
      }
    }
    //Case when aminoacids match
    if(this.aminoAcid == inList.aminoAcid){
      return this.codonDiff(inList) + this.next.codonCompare(inList.next);
    }
    //Cases when this has an aminoAcid that inList pointer does not
    if(this.aminoAcid < inList.aminoAcid){
      return this.totalCount() + this.next.codonCompare(inList);
    }
    //Cases when inList aminoAcid has an aminoacid that this does not
    return inList.totalCount() + this.codonCompare(inList.next);
  }


  /********************************************************************************************/
  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList(){
    //Base case for when the method reaches the end of the linked list
    if(this.next == null){
      return new char[]{this.aminoAcid};
    }
    //Create an array where it will store all characters after the current node
    char[] a = next.aminoAcidList();
    //Create an array where all the characters after the current node including the current character will be stored
    char[] net = new char[a.length+1];
    net[0] = aminoAcid;
    //Copy the characters to the newest array
    for (int i = 0; i < a.length; i++) {
      net[i+1] = a[i];
    }

    return net;

  }

  /********************************************************************************************/
  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts(){
    //Base case for when the method reaches the end of the linked list
    if(this.next == null){
      return new int[]{this.totalCount()};
    }
    //Create an array where it will store all the counts after the current node
    int[] a = next.aminoAcidCounts();
    //Create an array where all the counts after the current node including the counts of the current aminoacid will be stored
    int[] net = new int[a.length+1];
    net[0] = this.totalCount();
    //Copy the counts to the newest array
    for (int i = 0; i < a.length; i++) {
      net[i+1] = a[i];
    }
    return net;

  }


  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted(){
    //Base case. if the method reaches the end of the list without returning false, then the list is sorted
    if(this.next == null) return true;
    //Case where the method found out the list is not sorted, returns false
    if(this.aminoAcid > this.next.aminoAcid){
      return false;
    }
    //Recursive call to go to the next node
    return this.next.isSorted();
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
      //If the list is empty create the head and change what the next codon is
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
    //Pointers to not lose track the references of the lists
    AminoAcidLL beforeNode = inList;
    AminoAcidLL checkingNode = beforeNode.next;
    AminoAcidLL nextNode = null;
    AminoAcidLL head = inList;
    AminoAcidLL insertionPosition = null;
    //Loop that willl run until the list is fully sorted
    while(checkingNode != null){
      nextNode = checkingNode.next;
      //Find the position of insertion for the node
      insertionPosition = findInsertionPosition(head, checkingNode);
      //If statementes that take care of where the node will be placed
      if(insertionPosition == beforeNode){
        beforeNode = checkingNode;
      }else{
        beforeNode.next = checkingNode.next;
        //If to check if the node should be the first element of the list
        if(insertionPosition == null){
          checkingNode.next = head;
          head = checkingNode;
          //Else insert the node on its corresponding position
          }else{
          checkingNode.next = insertionPosition.next;
          insertionPosition.next = checkingNode;
        }
      }
      checkingNode = nextNode;
    }
    return head;
  }

  /********************************************************************************************/
  public void printList(){
    AminoAcidLL pointer = this;
    while(pointer != null) {
      System.out.print(pointer.aminoAcid);
      pointer = pointer.next;
    }
    System.out.println("");
  }
  /********************************************************************************************/
  private static AminoAcidLL findInsertionPosition(AminoAcidLL inList, AminoAcidLL aminoAcid){
    AminoAcidLL head = inList;
    char checkingLetter = aminoAcid.aminoAcid;
    AminoAcidLL position = null;
    AminoAcidLL checkingPosition = head;
    //Looks for after what node the node that is being checked should be introduced, where the aminoacid that is next has a greater value
    while(checkingPosition != null && checkingLetter > checkingPosition.aminoAcid ){
      position = checkingPosition;
      checkingPosition = checkingPosition.next;
    }
    return position;
  }
}