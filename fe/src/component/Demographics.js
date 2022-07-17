import React from 'react';
import DemographicsBanner from 'terra-demographics-banner';
import { injectIntl } from 'react-intl';

const BasicDemographicsBanner = () => (
  <DemographicsBanner
    age="25 Years"
    dateOfBirth="May 9, 1993"
    gender="Male"
    personName="Johnathon Doe"
    preferredFirstName="John"
  />
);

export default injectIntl(BasicDemographicsBanner);