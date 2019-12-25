package queuepattern;
import annotation.AuthorAnno;
import space.*;

@AuthorAnno
public interface HuluwaTeamQueuePattern {
    void generateTheCheerPattern(Space battleground, int x, int y);
    void generateChangshePattern(Space battleground, int x, int y);
    void generateChongePattern(Space battleground, int x, int y);
    void generateFangyuanPattern(Space battleground, int x, int y);
    void generateFengshiPattern(Space battleground, int x, int y);
    void generateHeyiPattern(Space battleground, int x, int y);
    void generateYanxingPattern(Space battleground, int x, int y);
    void generateYanyuePattern(Space battleground, int x, int y);
    void generateYulinPattern(Space battleground, int x, int y);
}
