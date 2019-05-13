package subway.algorithm.shortest;

import subway.component.NodeCountMap;
import subway.component.link.LinkContainer;
import subway.tool.VersionMark;

public class LeastUnpleasant extends ShortestPathModel {
    public LeastUnpleasant(LinkContainer linkContainer,
                           NodeCountMap nodeCountMap,
                           VersionMark versionMark) {
        super(linkContainer, nodeCountMap, versionMark);
    }

    @Override
    public Integer getEdgeValue(int actNodeI, int actNodeJ,
                                int pathIdI, int pathIdJ,
                                LinkContainer linkContainer) {
        if (actNodeI == actNodeJ) {
            if (pathIdI == pathIdJ) {
                return 0;
            } else {
                return 32;
            }
        } else {
            if (pathIdI == pathIdJ) {
                if (linkContainer.hasEdgeOnPath(actNodeI, actNodeJ, pathIdI)) {
                    int fx = Math.max((actNodeI % 5 + 5) % 5,
                        (actNodeJ % 5 + 5) % 5);
                    int hx = (int)Math.pow(4,fx);
                    return hx;
                }
            }
        }
        return null;
    }
}
