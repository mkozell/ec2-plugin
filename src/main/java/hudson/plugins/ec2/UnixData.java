package hudson.plugins.ec2;

import hudson.Extension;
import hudson.model.Descriptor;

import hudson.util.FormValidation;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class UnixData extends AMITypeData {
    private final String rootCommandPrefix;
    private final String sshPort;

    @DataBoundConstructor
    public UnixData(String rootCommandPrefix, String sshPort) {
        this.rootCommandPrefix = rootCommandPrefix;
        this.sshPort = sshPort;

        this.readResolve();
    }

    protected Object readResolve() {
        Jenkins.getInstance().checkPermission(Jenkins.RUN_SCRIPTS);
        return this;
    }

    @Override
    public boolean isWindows() {
        return false;
    }

    @Override
    public boolean isUnix() {
        return true;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<AMITypeData> {
        @Override
        public String getDisplayName() {
            return "unix";
        }

        @Restricted(NoExternalUse.class)
        public FormValidation doCheckRootCommandPrefix(@QueryParameter String value){
            if(StringUtils.isBlank(value) || Jenkins.getInstance().hasPermission(Jenkins.RUN_SCRIPTS)){
                return FormValidation.ok();
            }else{
                return FormValidation.error(Messages.General_MissingPermission());
            }
        }

        @Restricted(NoExternalUse.class)
        public FormValidation doCheckSlaveCommandPrefix(@QueryParameter String value){
            if(StringUtils.isBlank(value) || Jenkins.getInstance().hasPermission(Jenkins.RUN_SCRIPTS)){
                return FormValidation.ok();
            }else{
                return FormValidation.error(Messages.General_MissingPermission());
            }
        }
    }

    public String getRootCommandPrefix() {
        return rootCommandPrefix;
    }

    public String getSshPort() {
        return sshPort == null || sshPort.isEmpty() ? "22" : sshPort;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rootCommandPrefix == null) ? 0 : rootCommandPrefix.hashCode());
        result = prime * result + ((sshPort == null) ? 0 : sshPort.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final UnixData other = (UnixData) obj;
        if (rootCommandPrefix == null) {
            if (other.rootCommandPrefix != null)
                return false;
        } else if (!rootCommandPrefix.equals(other.rootCommandPrefix))
            return false;
        if (sshPort == null) {
            if (other.sshPort != null)
                return false;
        } else if (!sshPort.equals(other.sshPort))
            return false;
        return true;
    }
}
