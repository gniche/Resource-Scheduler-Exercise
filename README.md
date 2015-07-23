<BODY>
<DIV id="page_1">
<DIV id="dimg1">
</DIV>


<DIV id="id_1">
<P class="p0 ft0">Resource Scheduler</P>
<P class="p1 ft1">Current System</P>
<P class="p2 ft2">You are working on a system which uses a single, very expensive, external/3rd party resource to perform some, potentially very time consuming, operations on messages that you send to it. You are supplied with the Gateway and Message interfaces describing how to interact with the external resource:</P>
<P class="p3 ft2">send messages to be processed by calling the Gateway's send(Message msg) method:</P>
<P class="p4 ft2">public interface Gateway</P>
<P class="p5 ft2">public void send(Message msg)</P>
<P class="p6 ft2">when a Message has completed processing, its completed() method will be called:</P>
<P class="p8 ft1">Task</P>
<P class="p9 ft2">The number of these external resources has just been increased to allow more messages to be processed. However, as these resources are very expensive, we want to make sure that they are not idle when messages are waiting to be processed. You should implement a class or classes that:</P>
<P class="p3 ft2">can be configured with the number of resources available</P>
<P class="p10 ft2">receives Messages (and queues them up if they cannot be processed yet)</P>
<P class="p10 ft2">as available resources permit (or as they become available), sends the 'correct' message to the Gateway</P>
<P class="p11 ft3">Selecting the right message</P>
<P class="p12 ft2">Messages to the Gateway have a logical grouping and several Messages form a "group" (messages have a group ID).</P>
<P class="p13 ft2">Messages are not guaranteed to be delivered in their groups. I.E. you might get messages from group2 before you are finished with group1</P>
<P class="p14 ft2">Where possible, the message groups should not be interleaved...except where resources are idle and other work can be done. The priority in which to process groups is defined by the order in which you receive the first message from the group</P>
<P class="p15 ft2">This is captured as a set of <SPAN class="ft4">behaviors </SPAN>below:</P>
<P class="p11 ft3">Forwarding</P>
<P class="p16 ft2">The class you write must forward Messages via the Gateway interface when resources are available:</P>
<P class="p17 ft2">For a single resource, when one message is received, that message is sent to the gateway</P>
<P class="p10 ft2">For two resources, when two messages are received, both messages are sent to the gateway</P>
<P class="p11 ft3">Queuing</P>
<P class="p16 ft2">When no resources are available, messages should not be sent to the Gateway</P>
<P class="p17 ft2">For a single resource, when two messages are received, only the first message is sent to the gateway</P>
<P class="p18 ft3">Responding</P>
<P class="p15 ft2">As messages are completed, if there are queued messages, they should be processed</P>
<P class="p19 ft2">Same as the queuing above, but after the first message is completed, the second message is sent to the gateway</P>
<P class="p11 ft3">Prioritising</P>
</DIV>
<DIV id="id_2">
<P class="p20 ft2">If there are messages belonging to multiple groups in the queue, as resources become available, we want to prioritise messages from groups already started.</P>
</DIV>
</DIV>
<DIV id="page_2">
<DIV id="dimg1">
</DIV>


<DIV class="dclr"></DIV>
<P class="p21 ft2">For a single resource, messages received:</P>
<P class="p22 ft2">message1 (group2)</P>
<P class="p4 ft2">message2 (group1)</P>
<P class="p4 ft2">message3 (group2)</P>
<P class="p4 ft2">message4 (group3)</P>
<P class="p10 ft2">message1 (group2) was received first so will be processed first</P>
<P class="p6 ft2">as messages complete, the order they are sent to the gateway should be:</P>
<P class="p4 ft2">message1</P>
<P class="p4 ft2">message3 (it's part of group2, which is already <NOBR>"in-progress")</NOBR></P>
<P class="p23 ft2">message2 message4</P>
<P class="p8 ft1">Extra credit</P>
<P class="p24 ft2">Please extend your solution to include at least one of the following features:</P>
<P class="p11 ft3">Cancellation</P>
<P class="p25 ft2">It should be possible to tell the scheduler that a group of messages has now been cancelled. Once cancelled, no further messages from that group should sent to the Gateway.</P>
<P class="p26 ft3">Alternative Message Prioritisation</P>
<P class="p27 ft2">It should be possible to use different Message prioritisation algorithms to select the next Message from the queue. Invent a new strategy and allow the resource scheduler to be run with this or the original algorithm easily.</P>
<P class="p26 ft3">Termination Messages</P>
<P class="p28 ft2">When a Termination Message is received, that means that it is the last Message in that group (not all groups have the same number of messages). If further Messages belonging to that group are received, an error should be raised.</P>
<P class="p29 ft5">Submission</P>
<P class="p15 ft2">Please submit the following artifacts for review:</P>
<P class="p30 ft2">text file with a running commentary of what you are doing at each step and your thinking behind each key decision (like the conversation you'd have while writing it with someone else present or <NOBR>pair-programming)</NOBR></P>
<P class="p6 ft2">a solution that you would consider "production quality"</P>
<P class="p6 ft2">all source and project files required to build and run the task (an eclipse project would ideal)</P>
<P class="p31 ft2">all the tests written to support the work (we would love to see evidence of Test Driven Development!) any other design work you undertook</P>
<P class="p32 ft2">You should be able to reach a sensible conclusion to the exercise (you do not have to implement every suggestion in the linked article) in between <NOBR>1-4</NOBR> hours. Please submit it along with any instructions on building it when you are ready. Please try to minimise use of 3rd party libs to: JUnit, Log4J, perhaps a mocking framework. The aim is to see you test and build the classes, not just call libraries.</P>
</DIV>
</BODY>
</HTML>
