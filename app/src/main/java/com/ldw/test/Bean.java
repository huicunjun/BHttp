package com.ldw.test;

/**
 * @date 2020/5/27 7:50
 * @user 威威君
 */
public class Bean {
    @Override
    public String toString() {
        return "Bean{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }



    private String token;
    private UserBean user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        @Override
        public String toString() {
            return "UserBean{" +
                    "id=" + id +
                    ", nickname='" + nickname + '\'' +
                    ", signature='" + signature + '\'' +
                    ", state='" + state + '\'' +
                    ", txurl='" + txurl + '\'' +
                    ", city=" + city +
                    ", experience=" + experience +
                    ", grade=" + grade +
                    ", registrationid='" + registrationid + '\'' +
                    ", postCount=" + postCount +
                    ", fansCount=" + fansCount +
                    ", attentionCount=" + attentionCount +
                    ", stuname=" + stuname +
                    ", classname=" + classname +
                    ", department=" + department +
                    ", specialty=" + specialty +
                    ", fans=" + fans +
                    ", student=" + student +
                    '}';
        }

        private int id;
        private String nickname;
        private String signature;
        private String state;
        private String txurl;
        private Object city;
        private int experience;
        private int grade;
        private String registrationid;
        private int postCount;
        private int fansCount;
        private int attentionCount;
        private Object stuname;
        private Object classname;
        private Object department;
        private Object specialty;
        private boolean fans;
        private StudentBean student;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTxurl() {
            return txurl;
        }

        public void setTxurl(String txurl) {
            this.txurl = txurl;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public int getExperience() {
            return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getRegistrationid() {
            return registrationid;
        }

        public void setRegistrationid(String registrationid) {
            this.registrationid = registrationid;
        }

        public int getPostCount() {
            return postCount;
        }

        public void setPostCount(int postCount) {
            this.postCount = postCount;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(int attentionCount) {
            this.attentionCount = attentionCount;
        }

        public Object getStuname() {
            return stuname;
        }

        public void setStuname(Object stuname) {
            this.stuname = stuname;
        }

        public Object getClassname() {
            return classname;
        }

        public void setClassname(Object classname) {
            this.classname = classname;
        }

        public Object getDepartment() {
            return department;
        }

        public void setDepartment(Object department) {
            this.department = department;
        }

        public Object getSpecialty() {
            return specialty;
        }

        public void setSpecialty(Object specialty) {
            this.specialty = specialty;
        }

        public boolean isFans() {
            return fans;
        }

        public void setFans(boolean fans) {
            this.fans = fans;
        }

        public StudentBean getStudent() {
            return student;
        }

        public void setStudent(StudentBean student) {
            this.student = student;
        }

        public static class StudentBean {


            private int id;
            private int userid;
            private String stuid;
            private String stupass;
            private String stuname;
            private String classname;
            private String department;
            private String specialty;
            private String idcard;
            private Object birth;
            private String entrydate;
            private Object img;
            private String sex;
            private String home;
            private String xz;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }

            public String getStuid() {
                return stuid;
            }

            public void setStuid(String stuid) {
                this.stuid = stuid;
            }

            public String getStupass() {
                return stupass;
            }

            public void setStupass(String stupass) {
                this.stupass = stupass;
            }

            public String getStuname() {
                return stuname;
            }

            public void setStuname(String stuname) {
                this.stuname = stuname;
            }

            public String getClassname() {
                return classname;
            }

            public void setClassname(String classname) {
                this.classname = classname;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public String getSpecialty() {
                return specialty;
            }

            public void setSpecialty(String specialty) {
                this.specialty = specialty;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }

            public Object getBirth() {
                return birth;
            }

            public void setBirth(Object birth) {
                this.birth = birth;
            }

            public String getEntrydate() {
                return entrydate;
            }

            public void setEntrydate(String entrydate) {
                this.entrydate = entrydate;
            }

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getHome() {
                return home;
            }

            public void setHome(String home) {
                this.home = home;
            }

            public String getXz() {
                return xz;
            }

            public void setXz(String xz) {
                this.xz = xz;
            }
        }
    }
}
