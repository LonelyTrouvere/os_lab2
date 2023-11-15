// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Random;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  Vector<sProcess> processVector;
  Vector<sProcess> ticketVector;
  int runtime;
  Results result;
  String resultsFile;
  int quantum;

  public SchedulingAlgorithm(int runtime, Vector processVector, Results result){
    this.processVector = processVector;
    this.runtime = runtime;
    this.result = result;
    formTickets();
    this.resultsFile = "Summary-Processes";
    result.schedulingType = "Interactive (Preemptive)";
    result.schedulingName = "Lottery"; 
    this.quantum = 50;
  }

  private void formTickets(){
    for(sProcess p : processVector){
      for(int i =0; i < p.ticketNum; i++)
        ticketVector.add(p);
    }
  }

  private sProcess lottery(){
    int i = new Random().nextInt(ticketVector.size());
    return ticketVector.get(i);
  }

    public Results Run() {
    int comptime = 0;
    int completed = 0;

    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      while (comptime < runtime) {

        if(completed == processVector.size()){
          out.close();
          result.compuTime = comptime;
          return result;
        }

        sProcess process;
        while(true){
          process = lottery();
          if(!process.completed && !process.blocked){
            out.println("Process: " + process.ticketNum + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + ")");
            break;
          }
          if(process.blocked)
            process.blocked = false;
        }
        int allowedRun = Math.min(quantum, Math.min(process.cputime-process.cpudone, Math.min(process.ioblocking-process.ionext, runtime-comptime)));
        
        process.cpudone += allowedRun;
        process.ionext += allowedRun;
        comptime += allowedRun;

        if(process.ionext == process.ioblocking){
          out.println("Process: " + process.ticketNum + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + ")");
          process.blocked = true;
          process.numblocked++;
          process.ionext = 0;
        }

        if(process.cpudone == process.cputime){
          out.println("Process: " + process.ticketNum + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + ")");
          process.completed = true;
          completed++;
        }

        if(allowedRun == quantum){
          out.println("Process: " + process.ticketNum + " stoped... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + ")");
        }

      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;
    return result;
  }
}


