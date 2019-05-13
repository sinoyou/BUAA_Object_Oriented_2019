package subway.tool;

import subway.algorithm.shortest.LeastTransfer;
import subway.algorithm.shortest.LeastUnpleasant;
import subway.algorithm.shortest.LowestTicketPrice;
import subway.algorithm.shortest.ShortestPath;
import subway.algorithm.shortest.ShortestPathModel;
import subway.component.NodeCountMap;
import subway.component.link.LinkContainer;

public class AlgorithmFactory {

    private NodeCountMap nodeCountMap;
    private LinkContainer linkContainer;
    private VersionMark versionMark;

    public AlgorithmFactory(LinkContainer linkContainer,
                            NodeCountMap nodeCountMap,
                            VersionMark versionMark) {
        this.linkContainer = linkContainer;
        this.versionMark = versionMark;
        this.nodeCountMap = nodeCountMap;
    }

    public ShortestPathModel produce(String name) throws Exception {
        switch (name) {
            case "ShortestPath": {
                return new ShortestPath(linkContainer, nodeCountMap,
                    versionMark);
            }
            case "LowestPrice": {
                return new LowestTicketPrice(linkContainer, nodeCountMap,
                    versionMark);
            }
            case "LeastTransfer": {
                return new LeastTransfer(linkContainer, nodeCountMap,
                    versionMark);
            }
            case "LeastUnpleasant": {
                return new LeastUnpleasant(linkContainer, nodeCountMap,
                    versionMark);
            }
            default: {
                throw new Exception("Unknown Algorithm Type.");
            }
        }
    }
}
