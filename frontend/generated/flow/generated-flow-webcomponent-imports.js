import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_0 from 'Frontend/styles/styles.css?inline';

injectGlobalCss($cssFromFile_0.toString(), 'CSSImport end', document);
import '@vaadin/tooltip/theme/lumo/vaadin-tooltip.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/horizontal-layout/theme/lumo/vaadin-horizontal-layout.js';
import '@vaadin/button/theme/lumo/vaadin-button.js';
import 'Frontend/generated/jar-resources/buttonFunctions.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/text-field/theme/lumo/vaadin-text-field.js';
import '@vaadin/password-field/theme/lumo/vaadin-password-field.js';
import '@vaadin/notification/theme/lumo/vaadin-notification.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '7455d1495f579b49263183f55281671fd95453c41d27c011112aff3c1ec678cf') {
    pending.push(import('./chunks/chunk-29962bf0cb858625ba4dc2d925a218fd8df1ffec8d9ba8808917e845364171f2.js'));
  }
  if (key === '3af487cafca025f44710821c47bb8f5c6b158bd1e919339f5078a3eff8e5a186') {
    pending.push(import('./chunks/chunk-c49c1a55fa73ef76db17c2ef2c338ffc3d0711cbc2e799011ee04f13bcde029d.js'));
  }
  if (key === 'b98df76bd355c38cd504ac05f3bb6d768019ff4d601f621cb97a7197f03f638a') {
    pending.push(import('./chunks/chunk-4a239a04c3b6a12770c13a2560b8fe8abac2919bd801104697115a4b1e854571.js'));
  }
  if (key === '8b2693bdfe55de3cd7fe057b5d73911898f2e99d51475048e9bbfd35683300cf') {
    pending.push(import('./chunks/chunk-1f04901b1c8f0e3c44bff29d6e944842c2db1eb9e3ee3f9a95b750ca7e03ac27.js'));
  }
  if (key === '89c1102995988cd19e3e9f527082d70e3980f5ac91f84783c7e364255069f54b') {
    pending.push(import('./chunks/chunk-4a239a04c3b6a12770c13a2560b8fe8abac2919bd801104697115a4b1e854571.js'));
  }
  if (key === 'a21b34a1842b6e5fda2d7a1fdbeb687a50d118bf1aff57ff97f8cdb9841f4b18') {
    pending.push(import('./chunks/chunk-d21efea5c52d22efd1152da4dd9f267c010c6f4e8914ec9a164653a91b6b2f70.js'));
  }
  if (key === 'ced0e107400d74214090f38ec850320a31be027c798b2f09cf2df7ca10e2d5ea') {
    pending.push(import('./chunks/chunk-c49c1a55fa73ef76db17c2ef2c338ffc3d0711cbc2e799011ee04f13bcde029d.js'));
  }
  if (key === 'd3a4d51eb5c386106ba7c310c042b5931cb6644a88b86146c254633763c9b975') {
    pending.push(import('./chunks/chunk-194c7ebb224119c05fdcb8bc824c923f2bd1650f8133a505b9e313c462924207.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}