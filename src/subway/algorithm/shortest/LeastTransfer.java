package subway.algorithm.shortest;

import subway.component.NodeCountMap;
import subway.component.link.LinkContainer;
import subway.tool.VersionMark;

public class LeastTransfer extends ShortestPathModel {

    public LeastTransfer(LinkContainer linkContainer,
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
                return 1;
            }
        } else if (pathIdI == pathIdJ) {
            if (linkContainer.hasEdgeOnPath(actNodeI, actNodeJ, pathIdI)) {
                return 0;
            }
        }
        return null;
    }
}
