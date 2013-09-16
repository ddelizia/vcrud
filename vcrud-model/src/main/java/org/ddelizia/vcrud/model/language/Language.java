package org.ddelizia.vcrud.model.language;

import org.ddelizia.vcrud.model.system.Domain;
import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/09/13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Language extends VcrudModel{

    @Column (name = "code", unique = true, nullable = false)
    private String code;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="encodedMultiLanguageString",column=@Column(name="name"))})
    private MultilanguageString name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Domain> domains;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MultilanguageString getName() {
        return name;
    }

    public void setName(MultilanguageString name) {
        this.name = name;
    }

    public Collection<Domain> getDomains() {
        return domains;
    }

    public void setDomains(Collection<Domain> domains) {
        this.domains = domains;
    }
}
