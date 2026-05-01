import { TestBed } from '@angular/core/testing';

import { EffectifEvolution } from './effectif-evolution';

describe('EffectifEvolution', () => {
  let service: EffectifEvolution;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EffectifEvolution);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
