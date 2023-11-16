public class sProcess {
  public int id;
  public int cputime;
  public int ioblocking;
  public int cpudone;
  public int ionext;
  public int numblocked;
  public int numStoped;
  public int ticketNum = 2;
  public Boolean completed;
  public Boolean blocked;

  public sProcess (int id, int cputime, int ioblocking, int cpudone, int ionext, int numblocked, int ticketNum) {
    this.id = id;
    this.cputime = cputime;
    this.ioblocking = ioblocking;
    this.cpudone = cpudone;
    this.ionext = ionext;
    this.numblocked = numblocked;
    this.completed = false;
    this.blocked = false;
    this.ticketNum = ticketNum;
    this.numStoped = 0;
  } 	
}
