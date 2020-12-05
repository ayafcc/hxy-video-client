package cn.mahua.vod.bean;

public class ExpandCenter {

    /**
     * v1 : {"people_count":"0","view_count":"5"}
     * v2 : {"people_count":"1","view_count":"10"}
     * v3 : {"people_count":"3","view_count":"20"}
     * v4 : {"people_count":"6","view_count":"40"}
     * v5 : {"people_count":"10","view_count":"80"}
     */

    private V1Bean v1;
    private V2Bean v2;
    private V3Bean v3;
    private V4Bean v4;
    private V5Bean v5;

    public V1Bean getV1() {
        return v1;
    }

    public void setV1(V1Bean v1) {
        this.v1 = v1;
    }

    public V2Bean getV2() {
        return v2;
    }

    public void setV2(V2Bean v2) {
        this.v2 = v2;
    }

    public V3Bean getV3() {
        return v3;
    }

    public void setV3(V3Bean v3) {
        this.v3 = v3;
    }

    public V4Bean getV4() {
        return v4;
    }

    public void setV4(V4Bean v4) {
        this.v4 = v4;
    }

    public V5Bean getV5() {
        return v5;
    }

    public void setV5(V5Bean v5) {
        this.v5 = v5;
    }

    public static class V1Bean {
        /**
         * people_count : 0
         * view_count : 5
         */

        private String people_count;
        private String view_count;

        public String getPeople_count() {
            return people_count;
        }

        public void setPeople_count(String people_count) {
            this.people_count = people_count;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }
    }

    public static class V2Bean {
        /**
         * people_count : 1
         * view_count : 10
         */

        private String people_count;
        private String view_count;

        public String getPeople_count() {
            return people_count;
        }

        public void setPeople_count(String people_count) {
            this.people_count = people_count;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }
    }

    public static class V3Bean {
        /**
         * people_count : 3
         * view_count : 20
         */

        private String people_count;
        private String view_count;

        public String getPeople_count() {
            return people_count;
        }

        public void setPeople_count(String people_count) {
            this.people_count = people_count;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }
    }

    public static class V4Bean {
        /**
         * people_count : 6
         * view_count : 40
         */

        private String people_count;
        private String view_count;

        public String getPeople_count() {
            return people_count;
        }

        public void setPeople_count(String people_count) {
            this.people_count = people_count;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }
    }

    public static class V5Bean {
        /**
         * people_count : 10
         * view_count : 80
         */

        private String people_count;
        private String view_count;

        public String getPeople_count() {
            return people_count;
        }

        public void setPeople_count(String people_count) {
            this.people_count = people_count;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }
    }
}
