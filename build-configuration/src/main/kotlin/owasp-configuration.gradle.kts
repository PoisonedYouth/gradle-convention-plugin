plugins{
    id("org.owasp.dependencycheck")
}

dependencyCheck {
    autoUpdate=false
    cveValidForHours=1
    format=  org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL
}