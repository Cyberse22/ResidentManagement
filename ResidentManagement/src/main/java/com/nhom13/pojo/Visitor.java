/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "visitor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visitor.findAll", query = "SELECT v FROM Visitor v"),
    @NamedQuery(name = "Visitor.findById", query = "SELECT v FROM Visitor v WHERE v.id = :id"),
    @NamedQuery(name = "Visitor.findByName", query = "SELECT v FROM Visitor v WHERE v.name = :name"),
    @NamedQuery(name = "Visitor.findByRelation", query = "SELECT v FROM Visitor v WHERE v.relation = :relation"),
    @NamedQuery(name = "Visitor.findByCreatedDate", query = "SELECT v FROM Visitor v WHERE v.createdDate = :createdDate"),
    @NamedQuery(name = "Visitor.findByUpdatedDate", query = "SELECT v FROM Visitor v WHERE v.updatedDate = :updatedDate"),
    @NamedQuery(name = "Visitor.findByActive", query = "SELECT v FROM Visitor v WHERE v.active = :active"),
    @NamedQuery(name = "Visitor.findByStatus", query = "SELECT v FROM Visitor v WHERE v.status = :status")})
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message = "{visitor.name.nullErr}")
    @Size(min = 1, max = 45, message = "{visitor.name.sizeErr}")
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull(message = "{visitor.relation.nullErr}")
    @Size(min = 1, max = 45, message = "{visitor.relation.sizeErr}")
    @Column(name = "relation")
    private String relation;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
    private Date updatedDate;
    @Column(name = "active")
    private Short active;
    @Size(max = 45)
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "visitorId")
    @JsonIgnore
    private Set<ResidentVisitor> residentVisitorSet;

    public Visitor() {
    }

    public Visitor(Integer id) {
        this.id = id;
    }

    public Visitor(Integer id, String name, String relation) {
        this.id = id;
        this.name = name;
        this.relation = relation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Set<ResidentVisitor> getResidentVisitorSet() {
        return residentVisitorSet;
    }

    public void setResidentVisitorSet(Set<ResidentVisitor> residentVisitorSet) {
        this.residentVisitorSet = residentVisitorSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visitor)) {
            return false;
        }
        Visitor other = (Visitor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom13.pojo.Visitor[ id=" + id + " ]";
    }
    
}
