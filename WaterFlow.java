import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


class Node
{
    int no,cost, pipelength;
    String state;
    int nooff, offperiods[];
    
    Node(int n, String s, int c) //For BFS and DFS
    {
        no=n;
        state=s;
        cost=c;
    }
    
    //Node(nodeno,state,cost,offperiods[],nooff)
    Node(int n, String s, int c, int a[], int o)
    {
        no=n;
        state=s;
        cost=c;
        offperiods=a;
        nooff=o;
    }
    
    //For Graph Creation
    //Node(no, state, pipelength, nooff, offperiods[])
    Node(int n, String s, int p, int o, int a[])
    {
        no=n;
        state=s;
        nooff=o;
        offperiods=a;
        pipelength=p;
    }
    
    Node(int n, String s, int p, int o)
    {
        no=n;
        state=s;
        nooff=o;
        pipelength=p;
    }
    
    public String toString()
    {
    
        return ("("+state+","+pipelength+","+Arrays.toString(offperiods)+","+cost+")");
    }
 
}

class CheckNode implements Comparator<Node>
{
    @Override
    public int compare(Node n1, Node n2)
    {
        int costdiff=n1.cost-n2.cost;
        if(costdiff!=0)
            return costdiff;
        else    
            return n1.state.compareTo(n2.state);
    }
}

/**
 *
 * @author Shreya
 */
public class WaterFlow
{

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) 
    {
        Scanner file;
        try 
        {
            File output=new File("output.txt");
            PrintWriter pWriter = new PrintWriter(new FileWriter(output));
            
            
            file = new Scanner(new FileReader("test_cases/test1.txt"));
            
            
            int T=Integer.parseInt(file.nextLine());
            System.out.println("\\\\No of Test Cases:"+T);
        
            
        for(int t=0;t<T;t++)
        {
            System.out.println("***********************************************");
            System.out.println("T="+t);
            String task="";
            switch(task=file.nextLine())
            {
                
                case "BFS": 
                    System.out.println(task);
                    System.out.println("<task>         BFS");
                    BFS(file,pWriter);
                    break;
                
                case "DFS": 
                    System.out.println(task);
                    System.out.println("<task>        DFS");
                    DFS(file,pWriter);
                    break;
                
                case "UCS": 
                    System.out.println(task);
                    System.out.println("<task>         UCS");
                    UCS(file,pWriter);
                    break;
                default: System.out.println("Task:"+task);
                    break;
               
            }
            if(file.hasNextLine())
            {
                System.out.println(file.nextLine());
            }
        }
       pWriter.close();
        } 
        catch (Exception ex) 
        {
        System.out.println(ex);
        }
    }
   
    static void DFS(Scanner file, PrintWriter output)
    {
        String source=file.nextLine();
        String destinations[]=file.nextLine().split(" ");
        //file.nextLine(); //middle
        String middles[]=file.nextLine().split(" ");    //don't need
        
        int noofpipes=Integer.parseInt(file.nextLine());
        
        System.out.println("<source>:       "+source);
        System.out.println("<destination>:  "+Arrays.toString(destinations));
        System.out.println("<middle nodes>: "+Arrays.toString(middles));
        System.out.println("<#pipes>:       "+noofpipes);
        
        ArrayList<String> toNeigh = new ArrayList<String>();
        String Starts="";
        
        //Create and Store Graph
       	for(int i=0;i<noofpipes;i++)
	{   
                String s[]=file.nextLine().split(" ");
                System.out.println("s:"+Arrays.toString(s));
                if(!Starts.contains(s[0]))
                {    
                    Starts+=s[0]+" ";
                    toNeigh.add(s[1]);
                }
                else
                {
                    String temp[]=Starts.split(" ");
                    int index=Arrays.asList(temp).indexOf(s[0]);
                    System.out.println(Arrays.toString(temp));
                    System.out.println(index);
                    String temp1=toNeigh.get(index)+" "+s[1];
                    toNeigh.set(index,temp1);
                }
                
                System.out.println("Starts: "+Starts+";");
                System.out.println("Neighbours: "+toNeigh);
       	}
        System.out.println("Starts: "+Starts+";");
        String start[]=Starts.split(" ");
        System.out.println("start:"+Arrays.toString(start));
        System.out.println("Neighbours: "+toNeigh);
                
        
        System.out.println("Graph:");
        for(int i=0;i<start.length;i++)
        {
            System.out.println(start[i]+"|"+toNeigh.get(i));
        }
        
        //************Graph has been created
        
        int starttime=Integer.parseInt(file.nextLine());
        System.out.println("Start-time:"+starttime);
        
        
        if(Arrays.asList(destinations).contains(source))
        {   
            System.out.println(source+" "+(starttime%24)); //OUTPUT
            output.println(source+" "+(starttime%24));
            return;
        }
        
        
        ArrayList<Node> frontier=new ArrayList<Node>();
        ArrayList<Node> explored=new ArrayList<Node>();
        int nodecount=-1;
        Node temp=new Node(++nodecount,source,starttime);
        frontier.add(temp);
        System.out.println("Frontier: ["+frontier.get(0).state+"]");
        System.out.println("Explored: []");
        while(true)
        {
            if(frontier.isEmpty())
            {   
                System.out.println("none");
                output.println("none");
                return;
            }
            else
            {
                System.out.println("########");
             
                System.out.print("Frontier: [");
                for(Node f: frontier)
                {
                    System.out.print(f.state+" ");
                }
                System.out.println("]");
                System.out.print("Explored: [");
                for(Node e: explored)
                {
                    System.out.print(e.state+" ");
                }
                System.out.println("]");
                
                Node front=frontier.get(0);
                System.out.println("Front:"+front);
                explored.add(front);
                frontier.remove(0);
                
                if(Arrays.asList(destinations).contains(front.state)) //check if goal while popping
                    {   
                        System.out.println(front.state+" "+front.cost%24); //OUTPUT
                        output.println(front.state+" "+front.cost%24);
                        return;
                    }
                
                
                int index=Arrays.asList(start).indexOf(front.state);
                //System.out.println("start:"+Arrays.toString(start)+" index:"+index);
                
                if(index==-1)//if it has no kids
                    continue;
                
                String child[]=toNeigh.get(index).split(" ");
                System.out.println("Children:"+Arrays.toString(child));
                Arrays.sort(child);//in ascending order
                for(int i=child.length-1;i>=0;i--) //store in descending order
                {
                    temp=new Node(++nodecount,child[i],(front.cost+1));
                    int indexexp=indexInList(explored, temp); //not working
                    int indexfro=indexInList(frontier, temp);
                    System.out.println("Frontier index:"+indexfro+"Explored index:"+indexexp);
                    if((indexexp<0))//check if node in frontier or explored       //DEBUG
                    {
                        frontier.add(0,temp);
                    }
                }
                
            }
        }
 
    }
    
    static void BFS(Scanner file, PrintWriter output)
    {
        String source=file.nextLine();
        String destinations[]=file.nextLine().split(" ");
        //file.nextLine(); //middle
        String middles[]=file.nextLine().split(" ");    //don't need
        
        int noofpipes=Integer.parseInt(file.nextLine());
        
        System.out.println("<source>:       "+source);
        System.out.println("<destination>:  "+Arrays.toString(destinations));
        System.out.println("<middle nodes>: "+Arrays.toString(middles));
        System.out.println("<#pipes>:       "+noofpipes);
        
        ArrayList<String> toNeigh = new ArrayList<String>();
        String Starts="";
        
        //Create and Store Graph
       	for(int i=0;i<noofpipes;i++)
	{   
                String s[]=file.nextLine().split(" ");
                System.out.println("s:"+Arrays.toString(s));
                if(!Starts.contains(s[0]))
                {    
                    Starts+=s[0]+" ";
                    toNeigh.add(s[1]);
                }
                else
                {
                    String temp[]=Starts.split(" ");
                    int index=Arrays.asList(temp).indexOf(s[0]);
                    System.out.println(Arrays.toString(temp));
                    System.out.println(index);
                    String temp1=toNeigh.get(index)+" "+s[1];
                    toNeigh.set(index,temp1);
                }
                
                System.out.println("Starts: "+Starts+";");
                System.out.println("Neighbours: "+toNeigh);
       	}
        System.out.println("Starts: "+Starts+";");
        String start[]=Starts.split(" ");
        System.out.println("start:"+Arrays.toString(start));
        System.out.println("Neighbours: "+toNeigh);
                
        
        System.out.println("Graph:");
        for(int i=0;i<start.length;i++)
        {
            System.out.println(start[i]+"|"+toNeigh.get(i));
        }
        
        //************Graph has been created
        
        int starttime=Integer.parseInt(file.nextLine());
        System.out.println("Start-time:"+starttime);
        
        
        if(Arrays.asList(destinations).contains(source))
        {   
            System.out.println(source+" "+starttime%24); //OUTPUT
            output.println(source+" "+starttime%24);
            return;
        }
        
        
        ArrayList<Node> frontier=new ArrayList<Node>();
        ArrayList<Node> explored=new ArrayList<Node>();
        int nodecount=-1;
        Node temp=new Node(++nodecount,source,starttime);
        frontier.add(temp);
        System.out.println("Frontier: ["+frontier.get(0).state+"]");
        System.out.println("Explored: []");
        
        while(true)
        {
            if(frontier.isEmpty())
            {   
                System.out.println("none");
                output.println("none");
                return;
            }
            else
            {
                System.out.println("########");
             
                System.out.print("Frontier: [");
                for(Node f: frontier)
                {
                    System.out.print(f.state+" ");
                }
                System.out.println("]");
                System.out.print("Explored: [");
                for(Node e: explored)
                {
                    System.out.print(e.state+" ");
                }
                System.out.println("]");
                
                Node front=frontier.get(0);
                System.out.println("Front:"+front);
                explored.add(front);
                frontier.remove(0);
                
                if(Arrays.asList(destinations).contains(front.state)) //check if goal while popping
                {   
                    System.out.println(front.state+" "+(front.cost%24)); //OUTPUT
                    output.println(front.state+" "+(front.cost%24)); //OUTPUT
                    return;
                }
                
                
                int index=Arrays.asList(start).indexOf(front.state);
                //System.out.println("start:"+Arrays.toString(start)+" index:"+index);
                
                if(index==-1)//if it has no kids
                    continue;
                
                String child[]=toNeigh.get(index).split(" ");
                System.out.println("Children:"+Arrays.toString(child));
                Arrays.sort(child);//in ascending order
                for(int i=0;i<child.length;i++) //store in ascending order
                {
                    temp=new Node(++nodecount,child[i],(front.cost+1));
                    int indexexp=indexInList(explored, temp); //not working
                    int indexfro=indexInList(frontier, temp);
                    System.out.println("Frontier index:"+indexfro+"Explored index:"+indexexp);
                    if((indexexp<0)&&(indexfro<0))//check if node in frontier or explored       //DEBUG
                    {
                        frontier.add(temp);
                    }
                }
            }
        }
                
        
    }
    
 
    
//Node(nodeno,state,parent,depth,cost,offperiods[],nooff)
//Neighbour(state,pipelength,offperiods[],noff)
    
    
    static void UCS(Scanner file, PrintWriter output)
    {
        String source=file.nextLine();
        String destinations[]=file.nextLine().split(" ");
        //file.nextLine(); //middle
        String middles[]=file.nextLine().split(" ");    //don't need
        
        int noofpipes=Integer.parseInt(file.nextLine());
        
        System.out.println("<source>:       "+source);
        System.out.println("<destination>:  "+Arrays.toString(destinations));
        System.out.println("<middle nodes>: "+Arrays.toString(middles));
        System.out.println("<#pipes>:       "+noofpipes);
        
        ArrayList<ArrayList<Node>> toNeigh = new ArrayList<ArrayList<Node>>();
        String Starts="";
        
        //Create and Store Graph
       	for(int i=0;i<noofpipes;i++)
	{   
                String s[]=file.nextLine().split(" ");  //pipe info
                //System.out.println("s:"+Arrays.toString(s)); 
                
                int nooff=Integer.parseInt(s[3])*2;
                int offp[]=new int[nooff];
                int counter=4; //position where offperiods start
                for(int k=0;k<nooff;k++)
                {
                    String temp[]=s[counter++].split("-");
                    //System.out.println(Arrays.toString(temp));  //delete
                    offp[k]=Integer.parseInt(temp[0]);    
                    offp[++k]=Integer.parseInt(temp[1]);
                }
                
                if(!Starts.contains(s[0]))
                {    
                    Starts+=s[0]+" ";
                    //Node(no, state, pipelength, nooff, offperiods[])
                    ArrayList<Node> templist=new ArrayList<Node>();
                    Node t=new Node(0,s[1],Integer.parseInt(s[2]),nooff,offp);
                    templist.add(t);
                    toNeigh.add(templist);
                }
                else
                {
                    String temp[]=Starts.split(" ");
                    int index=Arrays.asList(temp).indexOf(s[0]);
                    //System.out.println(Arrays.toString(temp));
                    //System.out.println(index);
                    ArrayList<Node> templist=toNeigh.get(index);
                    Node t=new Node(templist.size(),s[1],Integer.parseInt(s[2]),nooff,offp);
                    templist.add(t);
                    toNeigh.set(index,templist);
                }
                
               // System.out.println("Starts: "+Starts+";");
               // System.out.println("Neighbours: "+toNeigh);
       	}
        //System.out.println("Starts: "+Starts+";");
        String start[]=Starts.split(" ");
        //System.out.println("start:"+Arrays.toString(start));
        //System.out.println("Neighbours: "+toNeigh);
                
        
        System.out.println("Graph:");
        for(int i=0;i<start.length;i++)
        {
            System.out.println(start[i]+"|"+toNeigh.get(i));
        }
        
        //************Graph has been created
        
        int starttime=Integer.parseInt(file.nextLine());
        System.out.println("Start-time:"+starttime);
        
        
        if(Arrays.asList(destinations).contains(source))
        {   
            System.out.println(source+" "+starttime%24); //OUTPUT
            output.println(source+" "+starttime%24); //OUTPUT
            return;
        }
        
        
        ArrayList<Node> frontier=new ArrayList<Node>();
        ArrayList<Node> explored=new ArrayList<Node>();
        int nodecount=-1;
        Node temp=new Node(++nodecount,source,starttime);
        frontier.add(temp);
        System.out.println("Frontier: ["+frontier.get(0).state+"]");
        System.out.println("Explored: []");
        
        
        while(true)
        {
            if(frontier.isEmpty())
            {   
                System.out.println("none");
                output.println("none");
                return;
            }
            else
            {
                System.out.println("########");
             
                System.out.print("Frontier: [");
                for(Node f: frontier)
                {
                    System.out.print(f.state+" ");
                }
                System.out.println("]");
                System.out.print("Explored: [");
                for(Node e: explored)
                {
                    System.out.print(e.state+" ");
                }
                System.out.println("]");
                
                //Pop frontier
                Node front=frontier.get(0);
                System.out.println("Front:"+front);
                explored.add(front);
                frontier.remove(0);
                
                //If front==goal
                if(Arrays.asList(destinations).contains(front.state)) //check if goal while popping
                {   
                    System.out.println(front.state+" "+(front.cost%24)); //OUTPUT
                    output.println(front.state+" "+(front.cost%24));
                    return;
                }
                
                               
                //Explore the frontier's children
                int index=Arrays.asList(start).indexOf(front.state);
                //System.out.println("start:"+Arrays.toString(start)+" index:"+index);
                
                if(index==-1)//if it has no kids
                    continue;
                
                ArrayList<Node> child=toNeigh.get(index);
                System.out.println("Children:"+child);
                
                int currenttime=(front.cost)%24;
                System.out.println("Current time:"+currenttime);
                System.out.println("Children:");    
                one: for(int i=0;i<child.size();i++) //store in ascending order
                {
                    //Node(nodeno,state,cost,offperiods[],nooff)
                    temp=new Node(++nodecount,child.get(i).state,front.cost+child.get(i).pipelength,child.get(i).offperiods,child.get(i).nooff);
                    //temp=child.get(i);
                    System.out.println("Child "+i+" :"+temp.state);
                    //temp.no=++nodecount;
                    //temp.cost=front.cost+temp.pipelength;
                    
                    
                    int indexexp=indexInList(explored, temp); //not working
                    int indexfro=indexInList(frontier, temp);
                    System.out.println("Frontier index:"+indexfro+" Explored index:"+indexexp);
                    
                    //Check for offperiods if node is valid
                    if(temp.nooff!=0)
                    {
                        int k1=(temp.nooff)/2;
                        for(int k=0;k<k1;k++)
                        {
                            if((currenttime>=temp.offperiods[k])&&(currenttime<=temp.offperiods[++k]))
                            {
                                System.out.println("Cant Choose "+temp+"! Offperiod!");
                                continue one;
                            }
                        }
                    }
                    //If child not in Frontier or Explored
                    if((indexexp<0)&&(indexfro<0))//if node is not in frontier or explored       //DEBUG
                    {
                        frontier.add(temp);
                        Collections.sort(frontier,new CheckNode()); //Sort based on pathcosts
                    }
                    //If child already in Frontier with higher path cost
                    else if((indexexp<0)&&(temp.cost<frontier.get(indexfro).cost))//if node is in frontier but not in explored
                    {
                        System.out.println("Update "+temp.state+" from "+frontier.get(indexfro).cost+" to "+temp.cost);
                        frontier.set(indexfro,temp);
                        Collections.sort(frontier,new CheckNode()); //Sort based on pathcosts
                    }
                }
            }
        }
        
    }
    
    
    static int indexInList(ArrayList<Node> al, Node n)
    {
        int count=-1;
        for(Node n1: al)
        {
            count++;
            if(n1.state.equals(n.state))
                return count; 
        }
        return -1;
    }
    
}



