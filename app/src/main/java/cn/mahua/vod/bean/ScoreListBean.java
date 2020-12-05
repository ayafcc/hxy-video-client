package cn.mahua.vod.bean;

import com.google.gson.annotations.SerializedName;

public class ScoreListBean {

    /**
     * list : {"1":{"group_id":1,"group_name":"游客","group_status":1,"group_type":",1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,","group_popedom":{"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}},"group_points_day":0,"group_points_week":0,"group_points_month":0,"group_points_year":0,"group_points_free":0},"2":{"group_id":2,"group_name":"默认会员","group_status":1,"group_type":",1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,","group_popedom":{"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}},"group_points_day":0,"group_points_week":0,"group_points_month":0,"group_points_year":0,"group_points_free":0},"3":{"group_id":3,"group_name":"VIP会员","group_status":1,"group_type":",1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,","group_popedom":{"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}},"group_points_day":10,"group_points_week":70,"group_points_month":300,"group_points_year":3600,"group_points_free":0}}
     */

    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * 1 : {"group_id":1,"group_name":"游客","group_status":1,"group_type":",1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,","group_popedom":{"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}},"group_points_day":0,"group_points_week":0,"group_points_month":0,"group_points_year":0,"group_points_free":0}
         * 2 : {"group_id":2,"group_name":"默认会员","group_status":1,"group_type":",1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,","group_popedom":{"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}},"group_points_day":0,"group_points_week":0,"group_points_month":0,"group_points_year":0,"group_points_free":0}
         * 3 : {"group_id":3,"group_name":"VIP会员","group_status":1,"group_type":",1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,","group_popedom":{"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}},"group_points_day":10,"group_points_week":70,"group_points_month":300,"group_points_year":3600,"group_points_free":0}
         */

        @SerializedName("1")
        private _$1BeanX _$1;
        @SerializedName("2")
        private _$2BeanXX _$2;
        @SerializedName("3")
        private _$3BeanXXX _$3;

        public _$1BeanX get_$1() {
            return _$1;
        }

        public void set_$1(_$1BeanX _$1) {
            this._$1 = _$1;
        }

        public _$2BeanXX get_$2() {
            return _$2;
        }

        public void set_$2(_$2BeanXX _$2) {
            this._$2 = _$2;
        }

        public _$3BeanXXX get_$3() {
            return _$3;
        }

        public void set_$3(_$3BeanXXX _$3) {
            this._$3 = _$3;
        }

        public static class _$1BeanX {
            /**
             * group_id : 1
             * group_name : 游客
             * group_status : 1
             * group_type : ,1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,
             * group_popedom : {"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}}
             * group_points_day : 0
             * group_points_week : 0
             * group_points_month : 0
             * group_points_year : 0
             * group_points_free : 0
             */

            private int group_id;
            private String group_name;
            private int group_status;
            private String group_type;
            private GroupPopedomBean group_popedom;
            private int group_points_day;
            private int group_points_week;
            private int group_points_month;
            private int group_points_year;
            private int group_points_free;

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public int getGroup_status() {
                return group_status;
            }

            public void setGroup_status(int group_status) {
                this.group_status = group_status;
            }

            public String getGroup_type() {
                return group_type;
            }

            public void setGroup_type(String group_type) {
                this.group_type = group_type;
            }

            public GroupPopedomBean getGroup_popedom() {
                return group_popedom;
            }

            public void setGroup_popedom(GroupPopedomBean group_popedom) {
                this.group_popedom = group_popedom;
            }

            public int getGroup_points_day() {
                return group_points_day;
            }

            public void setGroup_points_day(int group_points_day) {
                this.group_points_day = group_points_day;
            }

            public int getGroup_points_week() {
                return group_points_week;
            }

            public void setGroup_points_week(int group_points_week) {
                this.group_points_week = group_points_week;
            }

            public int getGroup_points_month() {
                return group_points_month;
            }

            public void setGroup_points_month(int group_points_month) {
                this.group_points_month = group_points_month;
            }

            public int getGroup_points_year() {
                return group_points_year;
            }

            public void setGroup_points_year(int group_points_year) {
                this.group_points_year = group_points_year;
            }

            public int getGroup_points_free() {
                return group_points_free;
            }

            public void setGroup_points_free(int group_points_free) {
                this.group_points_free = group_points_free;
            }

            public static class GroupPopedomBean {
                /**
                 * 1 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 2 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 3 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 4 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 5 : {"1":"1","2":"2"}
                 * 6 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 7 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 8 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 9 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 10 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 11 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 12 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 13 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 14 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 15 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 16 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 17 : {"1":"1","2":"2"}
                 * 18 : {"1":"1","2":"2"}
                 */

                @SerializedName("1")
                private _$1Bean _$1;
                @SerializedName("2")
                private _$2Bean _$2;
                @SerializedName("3")
                private _$3Bean _$3;
                @SerializedName("4")
                private _$4Bean _$4;
                @SerializedName("5")
                private _$5Bean _$5;
                @SerializedName("6")
                private _$6Bean _$6;
                @SerializedName("7")
                private _$7Bean _$7;
                @SerializedName("8")
                private _$8Bean _$8;
                @SerializedName("9")
                private _$9Bean _$9;
                @SerializedName("10")
                private _$10Bean _$10;
                @SerializedName("11")
                private _$11Bean _$11;
                @SerializedName("12")
                private _$12Bean _$12;
                @SerializedName("13")
                private _$13Bean _$13;
                @SerializedName("14")
                private _$14Bean _$14;
                @SerializedName("15")
                private _$15Bean _$15;
                @SerializedName("16")
                private _$16Bean _$16;
                @SerializedName("17")
                private _$17Bean _$17;
                @SerializedName("18")
                private _$18Bean _$18;

                public _$1Bean get_$1() {
                    return _$1;
                }

                public void set_$1(_$1Bean _$1) {
                    this._$1 = _$1;
                }

                public _$2Bean get_$2() {
                    return _$2;
                }

                public void set_$2(_$2Bean _$2) {
                    this._$2 = _$2;
                }

                public _$3Bean get_$3() {
                    return _$3;
                }

                public void set_$3(_$3Bean _$3) {
                    this._$3 = _$3;
                }

                public _$4Bean get_$4() {
                    return _$4;
                }

                public void set_$4(_$4Bean _$4) {
                    this._$4 = _$4;
                }

                public _$5Bean get_$5() {
                    return _$5;
                }

                public void set_$5(_$5Bean _$5) {
                    this._$5 = _$5;
                }

                public _$6Bean get_$6() {
                    return _$6;
                }

                public void set_$6(_$6Bean _$6) {
                    this._$6 = _$6;
                }

                public _$7Bean get_$7() {
                    return _$7;
                }

                public void set_$7(_$7Bean _$7) {
                    this._$7 = _$7;
                }

                public _$8Bean get_$8() {
                    return _$8;
                }

                public void set_$8(_$8Bean _$8) {
                    this._$8 = _$8;
                }

                public _$9Bean get_$9() {
                    return _$9;
                }

                public void set_$9(_$9Bean _$9) {
                    this._$9 = _$9;
                }

                public _$10Bean get_$10() {
                    return _$10;
                }

                public void set_$10(_$10Bean _$10) {
                    this._$10 = _$10;
                }

                public _$11Bean get_$11() {
                    return _$11;
                }

                public void set_$11(_$11Bean _$11) {
                    this._$11 = _$11;
                }

                public _$12Bean get_$12() {
                    return _$12;
                }

                public void set_$12(_$12Bean _$12) {
                    this._$12 = _$12;
                }

                public _$13Bean get_$13() {
                    return _$13;
                }

                public void set_$13(_$13Bean _$13) {
                    this._$13 = _$13;
                }

                public _$14Bean get_$14() {
                    return _$14;
                }

                public void set_$14(_$14Bean _$14) {
                    this._$14 = _$14;
                }

                public _$15Bean get_$15() {
                    return _$15;
                }

                public void set_$15(_$15Bean _$15) {
                    this._$15 = _$15;
                }

                public _$16Bean get_$16() {
                    return _$16;
                }

                public void set_$16(_$16Bean _$16) {
                    this._$16 = _$16;
                }

                public _$17Bean get_$17() {
                    return _$17;
                }

                public void set_$17(_$17Bean _$17) {
                    this._$17 = _$17;
                }

                public _$18Bean get_$18() {
                    return _$18;
                }

                public void set_$18(_$18Bean _$18) {
                    this._$18 = _$18;
                }

                public static class _$1Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$2Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$3Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$4Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$5Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }

                public static class _$6Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$7Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$8Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$9Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$10Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$11Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$12Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$13Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$14Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$15Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$16Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$17Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }

                public static class _$18Bean {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }
            }
        }

        public static class _$2BeanXX {
            /**
             * group_id : 2
             * group_name : 默认会员
             * group_status : 1
             * group_type : ,1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,
             * group_popedom : {"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}}
             * group_points_day : 0
             * group_points_week : 0
             * group_points_month : 0
             * group_points_year : 0
             * group_points_free : 0
             */

            private int group_id;
            private String group_name;
            private int group_status;
            private String group_type;
            private GroupPopedomBeanX group_popedom;
            private int group_points_day;
            private int group_points_week;
            private int group_points_month;
            private int group_points_year;
            private int group_points_free;

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public int getGroup_status() {
                return group_status;
            }

            public void setGroup_status(int group_status) {
                this.group_status = group_status;
            }

            public String getGroup_type() {
                return group_type;
            }

            public void setGroup_type(String group_type) {
                this.group_type = group_type;
            }

            public GroupPopedomBeanX getGroup_popedom() {
                return group_popedom;
            }

            public void setGroup_popedom(GroupPopedomBeanX group_popedom) {
                this.group_popedom = group_popedom;
            }

            public int getGroup_points_day() {
                return group_points_day;
            }

            public void setGroup_points_day(int group_points_day) {
                this.group_points_day = group_points_day;
            }

            public int getGroup_points_week() {
                return group_points_week;
            }

            public void setGroup_points_week(int group_points_week) {
                this.group_points_week = group_points_week;
            }

            public int getGroup_points_month() {
                return group_points_month;
            }

            public void setGroup_points_month(int group_points_month) {
                this.group_points_month = group_points_month;
            }

            public int getGroup_points_year() {
                return group_points_year;
            }

            public void setGroup_points_year(int group_points_year) {
                this.group_points_year = group_points_year;
            }

            public int getGroup_points_free() {
                return group_points_free;
            }

            public void setGroup_points_free(int group_points_free) {
                this.group_points_free = group_points_free;
            }

            public static class GroupPopedomBeanX {
                /**
                 * 1 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 2 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 3 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 4 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 5 : {"1":"1","2":"2"}
                 * 6 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 7 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 8 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 9 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 10 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 11 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 12 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 13 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 14 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 15 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 16 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 17 : {"1":"1","2":"2"}
                 * 18 : {"1":"1","2":"2"}
                 */

                @SerializedName("1")
                private _$1BeanXX _$1;
                @SerializedName("2")
                private _$2BeanX _$2;
                @SerializedName("3")
                private _$3BeanX _$3;
                @SerializedName("4")
                private _$4BeanX _$4;
                @SerializedName("5")
                private _$5BeanX _$5;
                @SerializedName("6")
                private _$6BeanX _$6;
                @SerializedName("7")
                private _$7BeanX _$7;
                @SerializedName("8")
                private _$8BeanX _$8;
                @SerializedName("9")
                private _$9BeanX _$9;
                @SerializedName("10")
                private _$10BeanX _$10;
                @SerializedName("11")
                private _$11BeanX _$11;
                @SerializedName("12")
                private _$12BeanX _$12;
                @SerializedName("13")
                private _$13BeanX _$13;
                @SerializedName("14")
                private _$14BeanX _$14;
                @SerializedName("15")
                private _$15BeanX _$15;
                @SerializedName("16")
                private _$16BeanX _$16;
                @SerializedName("17")
                private _$17BeanX _$17;
                @SerializedName("18")
                private _$18BeanX _$18;

                public _$1BeanXX get_$1() {
                    return _$1;
                }

                public void set_$1(_$1BeanXX _$1) {
                    this._$1 = _$1;
                }

                public _$2BeanX get_$2() {
                    return _$2;
                }

                public void set_$2(_$2BeanX _$2) {
                    this._$2 = _$2;
                }

                public _$3BeanX get_$3() {
                    return _$3;
                }

                public void set_$3(_$3BeanX _$3) {
                    this._$3 = _$3;
                }

                public _$4BeanX get_$4() {
                    return _$4;
                }

                public void set_$4(_$4BeanX _$4) {
                    this._$4 = _$4;
                }

                public _$5BeanX get_$5() {
                    return _$5;
                }

                public void set_$5(_$5BeanX _$5) {
                    this._$5 = _$5;
                }

                public _$6BeanX get_$6() {
                    return _$6;
                }

                public void set_$6(_$6BeanX _$6) {
                    this._$6 = _$6;
                }

                public _$7BeanX get_$7() {
                    return _$7;
                }

                public void set_$7(_$7BeanX _$7) {
                    this._$7 = _$7;
                }

                public _$8BeanX get_$8() {
                    return _$8;
                }

                public void set_$8(_$8BeanX _$8) {
                    this._$8 = _$8;
                }

                public _$9BeanX get_$9() {
                    return _$9;
                }

                public void set_$9(_$9BeanX _$9) {
                    this._$9 = _$9;
                }

                public _$10BeanX get_$10() {
                    return _$10;
                }

                public void set_$10(_$10BeanX _$10) {
                    this._$10 = _$10;
                }

                public _$11BeanX get_$11() {
                    return _$11;
                }

                public void set_$11(_$11BeanX _$11) {
                    this._$11 = _$11;
                }

                public _$12BeanX get_$12() {
                    return _$12;
                }

                public void set_$12(_$12BeanX _$12) {
                    this._$12 = _$12;
                }

                public _$13BeanX get_$13() {
                    return _$13;
                }

                public void set_$13(_$13BeanX _$13) {
                    this._$13 = _$13;
                }

                public _$14BeanX get_$14() {
                    return _$14;
                }

                public void set_$14(_$14BeanX _$14) {
                    this._$14 = _$14;
                }

                public _$15BeanX get_$15() {
                    return _$15;
                }

                public void set_$15(_$15BeanX _$15) {
                    this._$15 = _$15;
                }

                public _$16BeanX get_$16() {
                    return _$16;
                }

                public void set_$16(_$16BeanX _$16) {
                    this._$16 = _$16;
                }

                public _$17BeanX get_$17() {
                    return _$17;
                }

                public void set_$17(_$17BeanX _$17) {
                    this._$17 = _$17;
                }

                public _$18BeanX get_$18() {
                    return _$18;
                }

                public void set_$18(_$18BeanX _$18) {
                    this._$18 = _$18;
                }

                public static class _$1BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$2BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$3BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$4BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$5BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }

                public static class _$6BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$7BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$8BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$9BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$10BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$11BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$12BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$13BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$14BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$15BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$16BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$17BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }

                public static class _$18BeanX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }
            }
        }

        public static class _$3BeanXXX {
            /**
             * group_id : 3
             * group_name : VIP会员
             * group_status : 1
             * group_type : ,1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,
             * group_popedom : {"1":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"2":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"3":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"4":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"5":{"1":"1","2":"2"},"6":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"7":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"8":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"9":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"10":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"11":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"12":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"13":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"14":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"15":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"16":{"1":"1","2":"2","3":"3","4":"4","5":"5"},"17":{"1":"1","2":"2"},"18":{"1":"1","2":"2"}}
             * group_points_day : 10
             * group_points_week : 70
             * group_points_month : 300
             * group_points_year : 3600
             * group_points_free : 0
             */

            private int group_id;
            private String group_name;
            private int group_status;
            private String group_type;
            private GroupPopedomBeanXX group_popedom;
            private int group_points_day;
            private int group_points_week;
            private int group_points_month;
            private int group_points_year;
            private int group_points_free;

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public int getGroup_status() {
                return group_status;
            }

            public void setGroup_status(int group_status) {
                this.group_status = group_status;
            }

            public String getGroup_type() {
                return group_type;
            }

            public void setGroup_type(String group_type) {
                this.group_type = group_type;
            }

            public GroupPopedomBeanXX getGroup_popedom() {
                return group_popedom;
            }

            public void setGroup_popedom(GroupPopedomBeanXX group_popedom) {
                this.group_popedom = group_popedom;
            }

            public int getGroup_points_day() {
                return group_points_day;
            }

            public void setGroup_points_day(int group_points_day) {
                this.group_points_day = group_points_day;
            }

            public int getGroup_points_week() {
                return group_points_week;
            }

            public void setGroup_points_week(int group_points_week) {
                this.group_points_week = group_points_week;
            }

            public int getGroup_points_month() {
                return group_points_month;
            }

            public void setGroup_points_month(int group_points_month) {
                this.group_points_month = group_points_month;
            }

            public int getGroup_points_year() {
                return group_points_year;
            }

            public void setGroup_points_year(int group_points_year) {
                this.group_points_year = group_points_year;
            }

            public int getGroup_points_free() {
                return group_points_free;
            }

            public void setGroup_points_free(int group_points_free) {
                this.group_points_free = group_points_free;
            }

            public static class GroupPopedomBeanXX {
                /**
                 * 1 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 2 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 3 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 4 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 5 : {"1":"1","2":"2"}
                 * 6 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 7 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 8 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 9 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 10 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 11 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 12 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 13 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 14 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 15 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 16 : {"1":"1","2":"2","3":"3","4":"4","5":"5"}
                 * 17 : {"1":"1","2":"2"}
                 * 18 : {"1":"1","2":"2"}
                 */

                @SerializedName("1")
                private _$1BeanXXX _$1;
                @SerializedName("2")
                private _$2BeanXXX _$2;
                @SerializedName("3")
                private _$3BeanXX _$3;
                @SerializedName("4")
                private _$4BeanXX _$4;
                @SerializedName("5")
                private _$5BeanXX _$5;
                @SerializedName("6")
                private _$6BeanXX _$6;
                @SerializedName("7")
                private _$7BeanXX _$7;
                @SerializedName("8")
                private _$8BeanXX _$8;
                @SerializedName("9")
                private _$9BeanXX _$9;
                @SerializedName("10")
                private _$10BeanXX _$10;
                @SerializedName("11")
                private _$11BeanXX _$11;
                @SerializedName("12")
                private _$12BeanXX _$12;
                @SerializedName("13")
                private _$13BeanXX _$13;
                @SerializedName("14")
                private _$14BeanXX _$14;
                @SerializedName("15")
                private _$15BeanXX _$15;
                @SerializedName("16")
                private _$16BeanXX _$16;
                @SerializedName("17")
                private _$17BeanXX _$17;
                @SerializedName("18")
                private _$18BeanXX _$18;

                public _$1BeanXXX get_$1() {
                    return _$1;
                }

                public void set_$1(_$1BeanXXX _$1) {
                    this._$1 = _$1;
                }

                public _$2BeanXXX get_$2() {
                    return _$2;
                }

                public void set_$2(_$2BeanXXX _$2) {
                    this._$2 = _$2;
                }

                public _$3BeanXX get_$3() {
                    return _$3;
                }

                public void set_$3(_$3BeanXX _$3) {
                    this._$3 = _$3;
                }

                public _$4BeanXX get_$4() {
                    return _$4;
                }

                public void set_$4(_$4BeanXX _$4) {
                    this._$4 = _$4;
                }

                public _$5BeanXX get_$5() {
                    return _$5;
                }

                public void set_$5(_$5BeanXX _$5) {
                    this._$5 = _$5;
                }

                public _$6BeanXX get_$6() {
                    return _$6;
                }

                public void set_$6(_$6BeanXX _$6) {
                    this._$6 = _$6;
                }

                public _$7BeanXX get_$7() {
                    return _$7;
                }

                public void set_$7(_$7BeanXX _$7) {
                    this._$7 = _$7;
                }

                public _$8BeanXX get_$8() {
                    return _$8;
                }

                public void set_$8(_$8BeanXX _$8) {
                    this._$8 = _$8;
                }

                public _$9BeanXX get_$9() {
                    return _$9;
                }

                public void set_$9(_$9BeanXX _$9) {
                    this._$9 = _$9;
                }

                public _$10BeanXX get_$10() {
                    return _$10;
                }

                public void set_$10(_$10BeanXX _$10) {
                    this._$10 = _$10;
                }

                public _$11BeanXX get_$11() {
                    return _$11;
                }

                public void set_$11(_$11BeanXX _$11) {
                    this._$11 = _$11;
                }

                public _$12BeanXX get_$12() {
                    return _$12;
                }

                public void set_$12(_$12BeanXX _$12) {
                    this._$12 = _$12;
                }

                public _$13BeanXX get_$13() {
                    return _$13;
                }

                public void set_$13(_$13BeanXX _$13) {
                    this._$13 = _$13;
                }

                public _$14BeanXX get_$14() {
                    return _$14;
                }

                public void set_$14(_$14BeanXX _$14) {
                    this._$14 = _$14;
                }

                public _$15BeanXX get_$15() {
                    return _$15;
                }

                public void set_$15(_$15BeanXX _$15) {
                    this._$15 = _$15;
                }

                public _$16BeanXX get_$16() {
                    return _$16;
                }

                public void set_$16(_$16BeanXX _$16) {
                    this._$16 = _$16;
                }

                public _$17BeanXX get_$17() {
                    return _$17;
                }

                public void set_$17(_$17BeanXX _$17) {
                    this._$17 = _$17;
                }

                public _$18BeanXX get_$18() {
                    return _$18;
                }

                public void set_$18(_$18BeanXX _$18) {
                    this._$18 = _$18;
                }

                public static class _$1BeanXXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$2BeanXXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$3BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$4BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$5BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }

                public static class _$6BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$7BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$8BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$9BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$10BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$11BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$12BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$13BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$14BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$15BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$16BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     * 3 : 3
                     * 4 : 4
                     * 5 : 5
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;
                    @SerializedName("3")
                    private String _$3;
                    @SerializedName("4")
                    private String _$4;
                    @SerializedName("5")
                    private String _$5;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }

                    public String get_$3() {
                        return _$3;
                    }

                    public void set_$3(String _$3) {
                        this._$3 = _$3;
                    }

                    public String get_$4() {
                        return _$4;
                    }

                    public void set_$4(String _$4) {
                        this._$4 = _$4;
                    }

                    public String get_$5() {
                        return _$5;
                    }

                    public void set_$5(String _$5) {
                        this._$5 = _$5;
                    }
                }

                public static class _$17BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }

                public static class _$18BeanXX {
                    /**
                     * 1 : 1
                     * 2 : 2
                     */

                    @SerializedName("1")
                    private String _$1;
                    @SerializedName("2")
                    private String _$2;

                    public String get_$1() {
                        return _$1;
                    }

                    public void set_$1(String _$1) {
                        this._$1 = _$1;
                    }

                    public String get_$2() {
                        return _$2;
                    }

                    public void set_$2(String _$2) {
                        this._$2 = _$2;
                    }
                }
            }
        }
    }
}
